package org.tatrman.esotools.api.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        val config =
                HikariConfig().apply {
                    driverClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"
                    // TODO: Move to HOCON configuration
                    jdbcUrl =
                            "jdbc:sqlserver://localhost:1433;databaseName=ESODB;encrypt=true;trustServerCertificate=true;"
                    username = "sa"
                    password = "Password123!"
                    maximumPoolSize = 3
                    isAutoCommit = false
                    transactionIsolation = "TRANSACTION_REPEATABLE_READ"
                    validate()
                }
        val dataSource = HikariDataSource(config)
        Database.connect(dataSource)
    }

    fun <T> query(block: () -> T): T = transaction { block() }
}
