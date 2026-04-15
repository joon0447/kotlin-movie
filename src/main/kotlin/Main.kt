@file:OptIn(ExperimentalUuidApi::class)

import database.Database
import database.repository.MovieRepository
import database.repository.MovieScreeningRepository
import model.CinemaKiosk
import model.CinemaTime
import model.CinemaTimeRange
import model.movie.CurrentShowingMovie
import model.schedule.CinemaSchedule
import model.schedule.ScreenSchedule
import model.seat.Seat
import model.seat.SeatColumn
import model.seat.SeatGrade
import model.seat.SeatGroup
import model.seat.SeatRow
import java.time.LocalDateTime
import kotlin.uuid.ExperimentalUuidApi

fun main() {
    Database.init()

    val movieById = MovieRepository().findAllById()
    val movies = movieById.values.toList()
    val seatGroup =
        SeatGroup(
            seats =
                listOf(
                    Seat(SeatRow("B"), SeatColumn(2), SeatGrade.S),
                    Seat(SeatRow("B"), SeatColumn(1), SeatGrade.B),
                    Seat(SeatRow("A"), SeatColumn(2), SeatGrade.B),
                    Seat(SeatRow("A"), SeatColumn(1), SeatGrade.S),
                ),
        )
    val screenings = MovieScreeningRepository().findAllById(movieById, seatGroup)

    val screenSchedule =
        ScreenSchedule(
            screenId = "1",
            servicePeriod =
                CinemaTimeRange(
                    start = CinemaTime(LocalDateTime.of(2026, 4, 16, 6, 0)),
                    end = CinemaTime(LocalDateTime.of(2026, 4, 16, 23, 59)),
                ),
            movieScreenings = screenings,
        )

    val cinemaSchedule = CinemaSchedule(screenSchedules = listOf(screenSchedule))
    CinemaController(
        cinemaKiosk = CinemaKiosk(cinemaSchedule),
        currentShowingMovie = CurrentShowingMovie(movies),
    ).run()
}
