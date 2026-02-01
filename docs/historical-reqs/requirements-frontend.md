# ESO Tools

This file contains the requirements for the ESO Tools project.  

## Current State

This project already provides API access the (database of) ESO ERP system and provide tools to access the transaction data as:
- RESTful API
- GraphQL API
- gRPC API
- MCP Server

## Goal

This project will provide a frontend for the ESO Tools project.  

## Requirements

- The frontend will be a web application.
- The frontend will use the RESTful API to access the transaction data provided by the API Server.
- The frontend will use the GraphQL API to access the transaction data provided by the API Server.

- The frontend will provide agent-chat interface to the ESO ERP system. The agent chat frontend will communicate with the Agent - see requirements-agent.md for the agent service definition. The agent service will use the MCP Server to access the transaction data.

- The frontend will provide a dashboard / landing page where the user can either choose the underlying applications:
    - query the transactional data directly using the RESTful API or GraphQL API 
    - or use the agent chat interface to query the transactional data using the MCP Server.

- The frontend will use the OAuth2 to authenticate the user. The authentication will be done using the OAuth2 Bearer Token. We are using Keycloak as the identity provider.

## Planning

First, let's decide on the tech stack for the frontend. Please, analyze two options:
1. "native" TypeScript + Vue
2. Kotlin + Compose Multiplatform
Please, prepare a short analysis of the two options and recommend one. 

After we decide on the tech stack, we will prepare a detailed plan for implementing the frontend. Create meaningful stages that can be tested, and for each stage, create a task list "tasks-frontend-stage-X.md" where X is the stage number.  


