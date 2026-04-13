package model.payment.policy

import model.MovieReservationResult
import model.payment.DiscountPolicy
import model.payment.PayType

class PointDiscount(
    private val point: Int,
) : DiscountPolicy {
    override fun apply(
        price: Int,
        reservations: List<MovieReservationResult.Success>,
        payType: PayType,
    ): Int = price - point
}
