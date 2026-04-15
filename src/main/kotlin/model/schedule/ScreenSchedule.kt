package model.schedule

import model.CinemaTime
import model.CinemaTimeRange
import model.movie.Movie

class ScreenSchedule(
    private val screenId: String,
    private val servicePeriod: CinemaTimeRange,
    private val movieScreenings: List<MovieScreening>,
) {
    init {
        val startOutOfRange =
            movieScreenings.firstOrNull {
                !isContainServicePeriod(it.screenTime.start)
            }
        val endOutOfRange =
            movieScreenings.firstOrNull {
                !isContainServicePeriod(it.screenTime.end)
            }
        require(startOutOfRange == null) {
            "상영관 $screenId 에서 운영 시간($servicePeriod)보다 일찍 배정된 영화가 있습니다. - ${startOutOfRange?.movie} : ${startOutOfRange?.screenTime?.start} - ${startOutOfRange?.screenTime?.end}"
        }
        require(endOutOfRange == null) {
            "상영관 $screenId 에서 운영 시간($servicePeriod)보다 늦게 배정된 영화가 있습니다. - ${endOutOfRange?.movie} : ${endOutOfRange?.screenTime?.start} - ${endOutOfRange?.screenTime?.end}"
        }
        movieScreenings.forEachIndexed { index, current ->
            movieScreenings.drop(index + 1).forEach { other ->
                require(!current.screenTime.overlaps(other.screenTime)) {
                    "상영관 $screenId 의 상영 시간이 겹칩니다: 영화 - ${current.movie} ${current.screenTime} / ${other.movie} ${other.screenTime}"
                }
            }
        }
    }

    fun screeningOf(movie: Movie): List<MovieScreening> =
        movieScreenings.filter {
            it.movie == movie
        }

    fun isContainServicePeriod(time: CinemaTime): Boolean = servicePeriod.contains(time)

    override fun equals(other: Any?): Boolean {
        if (other is ScreenSchedule) {
            return this.screenId == other.screenId
        }
        return false
    }

    override fun hashCode(): Int = screenId.hashCode()
}
