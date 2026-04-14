package model.payment

import model.reservation.MovieReservationResult

interface DiscountPolicy {
    fun apply(
        price: Int,
        reservations: List<MovieReservationResult.Success>,
        payType: PayType,
    ): Int
}
