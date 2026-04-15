package database

import org.junit.jupiter.api.BeforeEach

abstract class DatabaseTest {
    @BeforeEach
    fun resetDatabase() {
        val testUrl = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1"
        Database.init(url = testUrl)
    }
}
