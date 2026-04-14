package model.reservation

import model.schedule.MovieScreening

class Reservations(
    private val items: MutableList<MovieReservationResult.Success> = mutableListOf(),
) {
    val all: List<MovieReservationResult.Success> get() = items.toList()

    fun canAccept(screening: MovieScreening): Boolean = items.none { it.conflictsWith(screening) }

    fun add(reservation: MovieReservationResult.Success) {
        items.add(reservation)
    }
}
