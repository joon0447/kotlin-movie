package model.reservation

import model.CinemaTimeRange
import model.movie.Movie
import model.schedule.MovieScreening
import model.seat.Seat

sealed class MovieReservationResult {
    data class Success(
        val movie: Movie,
        val screenTime: CinemaTimeRange,
        val seat: Seat,
    ) : MovieReservationResult() {
        fun conflictsWith(other: MovieScreening): Boolean = screenTime != other.screenTime && screenTime.overlaps(other.screenTime)
    }

    object Failed : MovieReservationResult()
}
