package org.tatrman.esotools.api

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.kotest.core.spec.style.StringSpec
import org.jetbrains.exposed.sql.Database
import org.testcontainers.containers.MSSQLServerContainer
import org.testcontainers.utility.DockerImageName

abstract class IntegrationTestBase(body: StringSpec.() -> Unit = {}) : StringSpec(body) {
    companion object {
        init {
            // Disable Ryuk to prevent cleanup issues in some environments (optional)
            System.setProperty("testcontainers.ryuk.disabled", "true")
        }

        val mssqlContainer =
                MSSQLServerContainer(
                                DockerImageName.parse("mcr.microsoft.com/mssql/server:2022-latest")
                        )
                        .acceptLicense()
                        .withInitScript(
                                "init.sql"
                        ) // Reads from src/test/resources/init.sql (need to move/copy it)

        val wireMockServer = WireMockServer(WireMockConfiguration.wireMockConfig().dynamicPort())

        init {
            // Start containers parallel if needed, or sequential
            mssqlContainer.start()
            wireMockServer.start()
            WireMock.configureFor("localhost", wireMockServer.port())

            // Connect DatabaseFactory to the container
            // We need to override the hardcoded config in DatabaseFactory or make it configurable.
            // For now, let's manually re-init Exposed with container properties.
            val config =
                    HikariConfig().apply {
                        driverClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"
                        jdbcUrl = mssqlContainer.jdbcUrl
                        username = mssqlContainer.username
                        password = mssqlContainer.password
                        validate()
                    }
            Database.connect(HikariDataSource(config))
        }
    }
}
