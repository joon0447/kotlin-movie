package database.default

import model.movie.Movie
import model.movie.MovieId
import model.movie.MovieName
import model.movie.RunningTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
object DefaultMovies {
    data class MovieRow(
        val id: String,
        val name: String,
        val runningTimeMinutes: Int,
    )

    val rows: List<MovieRow> =
        listOf(
            MovieRow(
                id = "00000000-0000-0000-0000-000000000001",
                name = "인터스텔라",
                runningTimeMinutes = 169,
            ),
            MovieRow(
                id = "00000000-0000-0000-0000-000000000002",
                name = "오펜하이머",
                runningTimeMinutes = 180,
            ),
        )

    fun all(): List<Movie> = rows.map { it.toMovie() }

    private fun MovieRow.toMovie(): Movie =
        Movie(
            id = MovieId(Uuid.parse(id)),
            name = MovieName(name),
            runningTime = RunningTime(runningTimeMinutes),
        )
}
