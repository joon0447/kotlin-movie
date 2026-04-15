package database

import database.repository.MovieRepository
import database.repository.MovieScreeningRepository

object DataInitializer {
    private val movieRepository = MovieRepository()
    private val screeningRepository = MovieScreeningRepository()

    fun initializeIfEmpty() {
        if (movieRepository.findAllById().isNotEmpty()) return
        movieRepository.save()
        screeningRepository.save()
    }
}
