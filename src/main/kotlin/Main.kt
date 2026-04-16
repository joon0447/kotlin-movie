@file:OptIn(ExperimentalUuidApi::class)

import database.Database
import database.repository.MovieScreeningRepository
import model.CinemaKiosk
import kotlin.uuid.ExperimentalUuidApi

fun main() {
    Database.init()
    CinemaController(
        cinemaKiosk = CinemaKiosk(),
        screeningRepository = MovieScreeningRepository(),
    ).run()
}
