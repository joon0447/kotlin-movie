package model.schedule

import model.movie.Movie

class CinemaSchedule(
    screenSchedules: List<ScreenSchedule>,
) {
    private val screenSchedules = screenSchedules.toList()

    init {
        require(screenSchedules.distinct().size == screenSchedules.size)
    }

    fun getMovieScreenings(movie: Movie): List<MovieScreening> = screenSchedules.flatMap { it.screeningOf(movie) }
}
