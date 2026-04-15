package database.repository

import database.Database
import database.default.DefaultScreenings
import model.CinemaTime
import model.CinemaTimeRange
import model.movie.Movie
import model.movie.MovieId
import model.schedule.MovieScreening
import model.seat.SeatGroup
import java.sql.ResultSet
import java.sql.Timestamp
import java.time.LocalDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class MovieScreeningRepository {
    fun save() {
        val sql =
            """
            MERGE INTO movie_screening (movie_id, screen_start, screen_end)
            KEY (movie_id, screen_start)
            VALUES (?, ?, ?)
            """.trimIndent()

        Database.connection().use { connection ->
            connection.prepareStatement(sql).use { preparedStatement ->
                DefaultScreenings.rows.forEach { row ->
                    preparedStatement.setString(1, row.movieId)
                    preparedStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.parse(row.startAt)))
                    preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.parse(row.endAt)))
                    preparedStatement.addBatch()
                }
                preparedStatement.executeBatch()
            }
        }
    }

    fun findAllById(
        movieById: Map<MovieId, Movie>,
        seatGroup: SeatGroup,
    ): List<MovieScreening> {
        val sql = "SELECT movie_id, screen_start, screen_end FROM movie_screening"
        val screenings = mutableListOf<MovieScreening>()

        Database.connection().use { connection ->
            connection.prepareStatement(sql).use { preparedStatement ->
                preparedStatement.executeQuery().use { result ->
                    while (result.next()) {
                        screenings += result.toMovieScreening(movieById, seatGroup)
                    }
                }
            }
        }
        return screenings
    }

    private fun ResultSet.toMovieScreening(
        movieById: Map<MovieId, Movie>,
        seatGroup: SeatGroup,
    ): MovieScreening {
        val movieId = MovieId(Uuid.parse(getString("movie_id")))
        val movie = movieById[movieId] ?: error("Movie with id $movieId not found")
        val screenTime =
            CinemaTimeRange(
                start = CinemaTime(getTimestamp("screen_start").toLocalDateTime()),
                end = CinemaTime(getTimestamp("screen_end").toLocalDateTime()),
            )
        return MovieScreening(
            movie = movie,
            screenTime = screenTime,
            seatGroup = seatGroup,
        )
    }
}
