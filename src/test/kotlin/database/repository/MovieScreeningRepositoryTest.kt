package database.repository

import database.DatabaseTest
import model.movie.Movie
import model.movie.MovieId
import model.seat.Seat
import model.seat.SeatColumn
import model.seat.SeatGrade
import model.seat.SeatGroup
import model.seat.SeatRow
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MovieScreeningRepositoryTest : DatabaseTest() {
    private val movieRepository = MovieRepository()
    private val screeningRepository = MovieScreeningRepository()
    private val seatGroup =
        SeatGroup(
            listOf(
                Seat(SeatRow("A"), SeatColumn(1), SeatGrade.S),
                Seat(SeatRow("A"), SeatColumn(2), SeatGrade.B),
            ),
        )

    private lateinit var movieById: Map<MovieId, Movie>

    @BeforeEach
    fun setUp() {
        movieRepository.save()
        movieById = movieRepository.findAllById()
    }

    @Test
    fun `DB에 저장된 상영 정보를 불러오면 SeatGroup이 배정된다`() {
        screeningRepository.save()
        val result = screeningRepository.findAllById(movieById, seatGroup)
        result.forEach { screening ->
            assertThat(screening.seatGroup).isEqualTo(seatGroup)
        }
    }
}
