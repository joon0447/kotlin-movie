package database.repository

import database.Database
import database.default.DefaultMovies
import model.movie.Movie
import model.movie.MovieId
import model.movie.MovieName
import model.movie.RunningTime
import java.sql.ResultSet
import kotlin.collections.plusAssign
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class MovieRepository {
    fun save() {
        val sql =
            """
            MERGE INTO movie (id, name, running_time_minutes)
            KEY (id)
            VALUES (?, ?, ?)
            """.trimIndent()

        Database.connection().use { connection ->
            connection.prepareStatement(sql).use { preparedStatement ->
                DefaultMovies.rows.forEach { row ->
                    preparedStatement.setString(1, row.id)
                    preparedStatement.setString(2, row.name)
                    preparedStatement.setInt(3, row.runningTimeMinutes)
                    preparedStatement.addBatch()
                }
                preparedStatement.executeBatch()
            }
        }
    }

    fun findAllById(): Map<MovieId, Movie> {
        val sql = "SELECT id, name, running_time_minutes FROM movie"
        val movies = mutableMapOf<MovieId, Movie>()

        Database.connection().use { connection ->
            connection.prepareStatement(sql).use { statement ->
                statement.executeQuery().use { result ->
                    while (result.next()) {
                        val id = MovieId(Uuid.parse(result.getString("id")))
                        movies[id] = result.toMovie()
                    }
                }
            }
        }
        return movies
    }

    private fun ResultSet.toMovie(): Movie =
        Movie(
            id = MovieId(Uuid.parse(getString("id"))),
            name = MovieName(getString("name")),
            runningTime = RunningTime(getInt("running_time_minutes")),
        )
}
