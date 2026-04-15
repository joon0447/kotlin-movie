@file:OptIn(ExperimentalUuidApi::class)

import database.Database
import model.CinemaData
import model.CinemaKiosk
import model.schedule.CinemaSchedule
import kotlin.uuid.ExperimentalUuidApi

fun main() {
    Database.init()
    val screenSchedules = CinemaData.initScreenSchedule()
    val cinemaSchedule = CinemaSchedule(screenSchedules = screenSchedules)
    CinemaController(
        cinemaKiosk = CinemaKiosk(cinemaSchedule),
        currentShowingMovie = CinemaData.initMovieCatalog(),
    ).run()
}
