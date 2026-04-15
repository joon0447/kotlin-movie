package database.default

object DefaultScreenings {
    data class ScreeningRow(
        val movieId: String,
        val startAt: String,
        val endAt: String,
    )

    val rows: List<ScreeningRow> =
        listOf(
            ScreeningRow(
                movieId = "00000000-0000-0000-0000-000000000001",
                startAt = "2026-04-16T13:30:00",
                endAt = "2026-04-16T16:19:00",
            ),
            ScreeningRow(
                movieId = "00000000-0000-0000-0000-000000000001",
                startAt = "2026-04-16T18:00:00",
                endAt = "2026-04-16T20:49:00",
            ),
            ScreeningRow(
                movieId = "00000000-0000-0000-0000-000000000002",
                startAt = "2026-04-16T10:00:00",
                endAt = "2026-04-16T13:00:00",
            ),
        )
}
