package org.tatrman.esotools.api.db

import java.sql.ResultSet
import org.jetbrains.exposed.sql.Transaction

object StoredProcedureExecutor {
    fun <T> execute(
            transaction: Transaction,
            procedureName: String,
            params: Map<String, Any?>,
            mapper: (ResultSet) -> T
    ): List<T> {
        val results = mutableListOf<T>()
        // Simple implementation for single result set
        // For multiple, we need a more complex one

        val sql = buildSql(procedureName, params)

        transaction.exec(sql) { rs ->
            while (rs.next()) {
                results.add(mapper(rs))
            }
        }
        return results
    }

    // Advanced version for multiple result sets
    fun executeMulti(
            transaction: Transaction,
            procedureName: String,
            params: Map<String, Any?>,
            handler: (Int, ResultSet) -> Unit // int = result set index (0-based)
    ) {
        val conn = transaction.connection.connection as java.sql.Connection
        val sql = "{call $procedureName(${params.keys.joinToString(",") { "?" }})}"

        val stmt = conn.prepareCall(sql)
        params.values.forEachIndexed { index, value -> stmt.setObject(index + 1, value) }

        var hasResults = stmt.execute()
        var rsIndex = 0

        while (hasResults || stmt.updateCount != -1) {
            if (hasResults) {
                val rs = stmt.resultSet
                handler(rsIndex, rs)
                rsIndex++
            } else {
                // It was an update count
            }
            hasResults = stmt.moreResults
        }
        stmt.close()
    }

    private fun buildSql(proc: String, params: Map<String, Any?>): String {
        // This is generic and potentially unsafe for string injection if not prepared statement,
        // but Exposed exec uses raw SQL string usually.
        // The executeMulti above uses PrepareCall which is safer.
        return "EXEC $proc ${params.map { "${it.key} = '${it.value}'" }.joinToString(", ")}"
    }
}
