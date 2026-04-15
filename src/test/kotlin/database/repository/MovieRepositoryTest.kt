package database.repository

import database.DatabaseTest
import database.default.DefaultMovies
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MovieRepositoryTest : DatabaseTest() {
    private val repository = MovieRepository()

    @Test
    fun `영화 저장 후 영화 개수는 DefaultMovies에 등록된 영화 개수와 같다`() {
        repository.save()
        val result = repository.findAllById()
        assertThat(result.size).isEqualTo(DefaultMovies.rows.size)
    }

    @Test
    fun `저장된 영화와 DefaultMovies에 등록된 영화는 모두 같다`() {
        repository.save()
        val expectedMovies = DefaultMovies.all().toSet()
        val movies = repository.findAllById().values.toSet()

        assertThat(movies).isEqualTo(expectedMovies)
    }
}
