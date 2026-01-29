from typing import TypedDict, Annotated
from langgraph.graph import StateGraph, END
from langchain_core.messages import HumanMessage, BaseMessage
import operator

from ..config import settings

# Define State
class AgentState(TypedDict):
    messages: Annotated[list[BaseMessage], operator.add]

# Define Nodes
def call_model(state: AgentState):
    # In a real scenario, this would call AzureChatOpenAI
    # For scaffolding with mock keys, we just echo.
    last_message = state["messages"][-1]
    return {"messages": [HumanMessage(content=f"Echo: {last_message.content}")]}

# Define Graph
workflow = StateGraph(AgentState)
workflow.add_node("agent", call_model)
workflow.set_entry_point("agent")
workflow.add_edge("agent", END)

app = workflow.compile()

async def run_agent(question: str) -> str:
    inputs = {"messages": [HumanMessage(content=question)]}
    result = await app.ainvoke(inputs)
    return result["messages"][-1].content
