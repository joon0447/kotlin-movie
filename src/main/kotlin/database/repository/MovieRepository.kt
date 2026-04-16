package database.repository

import database.Database
import database.default.DefaultMovies
import kotlin.collections.plusAssign
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
class MovieRepository {
    fun save() {
        val sql =
            """
            MERGE INTO movie (id, name, running_time_minutes)
            KEY (id)
            VALUES (?, ?, ?)
            """.trimIndent()

        Database.connection().use { connection ->
            connection.prepareStatement(sql).use { preparedStatement ->
                DefaultMovies.rows.forEach { row ->
                    preparedStatement.setString(1, row.id)
                    preparedStatement.setString(2, row.name)
                    preparedStatement.setInt(3, row.runningTimeMinutes)
                    preparedStatement.addBatch()
                }
                preparedStatement.executeBatch()
            }
        }
    }
}
