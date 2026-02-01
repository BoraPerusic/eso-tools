# ESO Tools

This file contains the requirements for the ESO Tools project.  

## Goal

This project will access the (database of) ESO ERP system and provide tools to access the transaction data as:
- RESTful API
- GraphQL API
- gRPC API
- MCP Server

## The Tools

There will be initially these different tools exposed as APIs:
- productStock: for a given product, get the stock information:
    - how much of this product is in stock
    - when this product will be restocked, based on
        - if purchased goods, the delivery date of the pending orders
        - if manufactured goods, the production date of the production orders
- orderStatus: for a given sales order, get the status of the order
    - accepts order number, or customer identifier; if the latter is provided, returns the states of all open orders for that customer
    - if the order is completed, get the delivery date
    - payment status is a part of the order status
    - the system recognizes also incomplete orders in the "basket" of the e-shop for a given customer
- returnStatus: if a return (complaint) exists for a given customer, get the status of the return
    - accepts customer identifier
    - if the return should be handled by returning (withdrawing) the goods, planned date for the withdrawal
    - if the return should be handled by refunding the payment, planned date for the refund 

## Tech Stack

Please, review the AGENTS.md file for tech stack. This application will be built using Kotlin + Ktor, no Spring framework. The MCP server will be built using the latest version of the Koog framework. For the rest of the technologies, please, review the AGENTS.md file.

The access to the ESO ERP system will be implemented byt accessing the transactional database of the ESO ERP system, which is a MS SQL Server database. The application will access the database and for each tool, it will execute the appropriate stored procedure to get the data. The stored procedures are already available in the ESO ERP system. 
We wil use the Exposed framework to retrieve the data from the database.  

## Project Structure

The APIs (REST, gRPC and GraphQL) will be implemented in one artifact (deployment). The MCP server will be implemented in another artifact (deployment). Both servers will be deployed as separate Docker containers. The MCP Server will use the API Server as a client to get the data. 



        