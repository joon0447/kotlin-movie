package database.repository

import database.DatabaseTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MovieScreeningRepositoryTest : DatabaseTest() {
    private val movieRepository = MovieRepository()
    private val screeningRepository = MovieScreeningRepository()

    @BeforeEach
    fun setUp() {
        movieRepository.save()
        screeningRepository.save()
    }

    @Test
    fun `존재하는 영화 이름으로 조회하면 상영 목록을 반환한다`() {
        val result = screeningRepository.findScreeningsByMovieName("인터스텔라")
        assertThat(result).isNotNull
        assertThat(result).isNotEmpty
    }

    @Test
    fun `존재하지 않는 영화 이름으로 조회하면 null을 반환한다`() {
        val result = screeningRepository.findScreeningsByMovieName("이영화는존재하지않아요")
        assertThat(result).isNull()
    }
}
