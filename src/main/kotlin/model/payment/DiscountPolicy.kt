package model.payment

import model.MovieReservationResult

interface DiscountPolicy {
    fun apply(
        price: Int,
        reservations: List<MovieReservationResult.Success>,
        payType: PayType,
    ): Int
}
