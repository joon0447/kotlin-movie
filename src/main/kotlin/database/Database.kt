package database

import java.sql.Connection
import java.sql.DriverManager

object Database {
    private const val DEFAULT_URL = "jdbc:h2:./data/cinema;DB_CLOSE_DELAY=-1"

    fun init(url: String = DEFAULT_URL) {
        val schema = loadSchema()
        connection(url).use { connection ->
            connection.createStatement().use { it.execute(schema) }
        }
        DataInitializer.initializeIfEmpty()
    }

    fun connection(url: String = DEFAULT_URL): Connection = DriverManager.getConnection(url, "sa", "")

    private fun loadSchema(): String {
        val stream =
            Database::class.java.classLoader.getResourceAsStream("schema.sql")
                ?: error("schema.sql 이 없습니다.")
        return stream.bufferedReader().use { it.readText() }
    }
}
