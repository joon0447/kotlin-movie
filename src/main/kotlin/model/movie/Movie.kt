package model.movie

import model.CinemaTimeRange
import java.util.Objects

class Movie(
    val name: MovieName,
    val id: MovieId,
    private val runningTime: RunningTime,
) {
    fun isSameDuration(cinemaTimeRange: CinemaTimeRange): Boolean = runningTime.isSameDuration(cinemaTimeRange)

    override fun equals(other: Any?): Boolean {
        if (other is Movie) {
            return this.id == other.id && this.name == other.name
        }
        return false
    }

    override fun hashCode(): Int = Objects.hash(id, name)
}
