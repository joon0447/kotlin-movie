package model.payment

import model.MovieReservationResult

class MoviePayment(
    val reservations: List<MovieReservationResult.Success>,
    private val policies: List<DiscountPolicy>,
) {
    val originalPrice: Int = reservations.sumOf { it.seat.price }

    fun getFinalPrice(payType: PayType): Int =
        policies.fold(originalPrice) { acc, policy ->
            policy.apply(acc, reservations, payType)
        }
}
