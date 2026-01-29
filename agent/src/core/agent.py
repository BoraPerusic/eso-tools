from typing import TypedDict, Annotated, Literal
import operator

from langchain_core.messages import BaseMessage, HumanMessage, AIMessage, SystemMessage
from langchain_openai import AzureChatOpenAI
from langgraph.graph import StateGraph, END, START
from langgraph.prebuilt import ToolNode

from ..config import settings
from ..mcp_client.client_manager import mcp_client

# Define State
class AgentState(TypedDict):
    messages: Annotated[list[BaseMessage], operator.add]

# Define Nodes
async def call_model(state: AgentState):
    """
    Calls the LLM with the current conversation history.
    It dynamically fetches tools from the MCP client if not already cached/bound.
    (In a real production app, we might cache these at startup, but here we fetch to ensure freshness)
    """
    # 1. Fetch Tools from MCP
    try:
        mcp_tools_data = await mcp_client.get_tools()
        
        # Convert MCP tools to LangChain compatible tools
        # We need to wrap the MCP tool call. 
        # Since LangChain's bind_tools expects standard tool schemas, we construct them locally
        # or use a wrapper. specific wrapper for mcp is not yet standard in langchain-core
        # so we will use a generic structured tool or simple function binding.
        
        # Simpler approach for Phase 2: Create a dynamic function for each tool
        formatted_tools = []
        tool_map = {}
        
        for tool in mcp_tools_data:
             # Create a callable for the tool
            async def generic_tool_handler(*args, __tool_name=tool.name, **kwargs):
                return await mcp_client.call_tool(__tool_name, kwargs)
            
            # Set metadata
            generic_tool_handler.__name__ = tool.name
            generic_tool_handler.__doc__ = tool.description
            
            # TODO: Improve generic Pydantic model creation from JSON schema for strict validation
            # For now, we rely on the LLM to adhere to the schema and pass dicts.
            
            formatted_tools.append(generic_tool_handler)
            tool_map[tool.name] = generic_tool_handler

    except Exception as e:
        # If MCP is down, we proceed without tools or fail.
        # For now, let's log and proceed (model might hallucinate or say it can't help)
        # formatted_tools = []
        # In this strict implementation, we fail if we can't get tools?
        # Let's return error message
        return {"messages": [AIMessage(content=f"System Error: Could not connect to MCP Server. {e}")]}

    # 2. Bind tools to Model
    model = AzureChatOpenAI(
        azure_endpoint=settings.AZURE_OPENAI_ENDPOINT,
        api_key=settings.AZURE_OPENAI_API_KEY,
        deployment_name=settings.AZURE_OPENAI_DEPLOYMENT_NAME,
        api_version="2024-02-15-preview", # Verify version
    )
    
    # We need to bind the tools using the structured tool definition
    # LangChain's bind_tools is smart enough to handle functions with docstrings.
    # However, without Pydantic models, it might be weak.
    # A robust implementation would parse tool.inputSchema into a dynamic Pydantic model.
    
    # IMPORTANT: For this scaffold, we might need to assume the tool definitions are passed correctly.
    # Let's try binding the generic handlers.
    
    # Re-instantiating model here is inefficient; should be global/cached.
    # But we need tools to bind.
    
    # NOTE: To make this robust, we should create a Tool wrapper. 
    # For this iteration, let's assume we use a specialized approach:
    # We will manually construct the OpenAI Tool definition if needed, 
    # but let's try the high-level API first.
    
    if formatted_tools:
        model = model.bind_tools(formatted_tools)

    response = await model.ainvoke(state["messages"])
    return {"messages": [response]}


def should_continue(state: AgentState) -> Literal["tools", END]:
    messages = state["messages"]
    last_message = messages[-1]
    if last_message.tool_calls:
        return "tools"
    return END

# Define Graph
workflow = StateGraph(AgentState)

workflow.add_node("agent", call_model)
# ToolNode is a convenient prebuilt node that executes tool calls.
# We need to provide it the tools.
# Since we fetch tools dynamically in 'agent', passing them to 'tools' node is tricky if static.
# We will assume tools don't change often and fetch them once or handle it differently.
# REFACTOR: Let's fetch tools globally or on startup in main.py and pass them here.
# For now, to keep it simple, we can't use prebuilt ToolNode easily if tools are dynamic per request
# without a custom node.
# Let's implement a custom tool execution node.

async def tool_node(state: AgentState):
    messages = state["messages"]
    last_message = messages[-1]
    
    results = []
    for tool_call in last_message.tool_calls:
        tool_name = tool_call["name"]
        arguments = tool_call["args"]
        
        try:
            result = await mcp_client.call_tool(tool_name, arguments)
            # Result is CallToolResult
            content = result.content[0].text # simplified
            results.append(
                {"tool_call_id": tool_call["id"], "role": "tool", "name": tool_name, "content": content}
            )
        except Exception as e:
             results.append(
                {"tool_call_id": tool_call["id"], "role": "tool", "name": tool_name, "content": f"Error: {e}"}
            )
            
    # We return ToolMessages
    # LangChain expects ToolMessage objects
    from langchain_core.messages import ToolMessage
    tool_messages = [
        ToolMessage(tool_call_id=r["tool_call_id"], content=r["content"], name=r["name"])
        for r in results
    ]
    return {"messages": tool_messages}

workflow.add_node("tools", tool_node)

workflow.set_entry_point("agent")
workflow.add_conditional_edges("agent", should_continue, ["tools", END])
workflow.add_edge("tools", "agent")

app = workflow.compile()

async def run_agent(question: str) -> str:
    # Ensure MCP is connected (managed by main.py lifecycle, but check here?)
    # We assume main.py calls `mcp_client.connect()` context.
    # Actually, context manager in main is for the lifespan.
    
    inputs = {"messages": [HumanMessage(content=question)]}
    result = await app.ainvoke(inputs)
    return result["messages"][-1].content
