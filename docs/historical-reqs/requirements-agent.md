# ESO Tools

This file contains the requirements for the ESO Tools project.  

## Current State

This project already provides API access the (database of) ESO ERP system and provide tools to access the transaction data as:
- RESTful API
- GraphQL API
- gRPC API
- MCP Server

## Goal

This project will create an AI Agent that will support natural language queries to the ESO ERP system. The agent will be able to answer questions like:

- "What is the stock of product X?"
- "When will product X be restocked?"
- "What is the status of order Y?"
- "What is the status of return Z?"

The agent will use the MCP Server to access the transaction data.

## Requirements

The agent will be a standalone module in this project. It will be built with Python using the following frameworks:
- FastAPI for the web framework
- LangChain for the agent framework
- It will be able to call the OpenAI API to get the response from the LLM. The initial implementation will access Azure Foundry (Azure OpenAI).
- It will use the MCP Server to get the transaction data. 
- The agent will communicate with the MCP Server using the MCP protocol.


## Planning
Prepare a detailed plan for implementing the agent. Create meaningful stages that can be tested, and for each stage, create a task list "tasks-agent-stage-X.md" where X is the stage number.
Review carefully the AGENTS.md file for the tech stack and other requirements.
Review the "docs" directory for the documentation of the project.

