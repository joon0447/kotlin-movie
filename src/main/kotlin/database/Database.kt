package database

import java.sql.Connection
import java.sql.DriverManager

object Database {
    private const val URL = "jdbc:h2:./data/cinema;DB_CLOSE_DELAY=-1"

    fun init() {
        val schema = loadSchema()
        connection().use { connection ->
            connection.createStatement().use { it.execute(schema) }
        }
    }

    fun connection(): Connection = DriverManager.getConnection(URL, "sa", "")

    private fun loadSchema(): String {
        val stream =
            Database::class.java.classLoader.getResourceAsStream("schema.sql")
                ?: error("schema.sql 이 없습니다.")
        return stream.bufferedReader().use { it.readText() }
    }
}
