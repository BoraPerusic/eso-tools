package org.tatrman.esotools.mcp

import io.modelcontextprotocol.kotlin.sdk.server.Server
import io.modelcontextprotocol.kotlin.sdk.server.StdioServerTransport
import io.modelcontextprotocol.kotlin.sdk.types.Implementation
import io.modelcontextprotocol.kotlin.sdk.types.ServerCapabilities
import kotlinx.coroutines.runBlocking
import kotlinx.io.asSink
import kotlinx.io.asSource
import kotlinx.io.buffered
import org.tatrman.esotools.mcp.client.EsoApiClient

fun main() = runBlocking {
        val client = EsoApiClient()
        val toolsLogic = Tools(client)

        val transport =
                StdioServerTransport(
                        System.`in`.asSource().buffered(),
                        System.out.asSink().buffered()
                )

        val server =
                Server(
                        serverInfo = Implementation(name = "eso-mcp-server", version = "1.0.0"),
                        options =
                                io.modelcontextprotocol.kotlin.sdk.server.ServerOptions(
                                        capabilities =
                                                ServerCapabilities(
                                                        tools =
                                                                ServerCapabilities.Tools(
                                                                        listChanged = true
                                                                )
                                                )
                                )
                )

        server.addTool(toolsLogic.listProductStockTool, toolsLogic::productStockCallback)
        server.addTool(toolsLogic.checkOrderStatusTool, toolsLogic::orderStatusCallback)
        server.addTool(toolsLogic.checkReturnStatusTool, toolsLogic::returnStatusCallback)

        server.createSession(transport)
        transport.start()
}
