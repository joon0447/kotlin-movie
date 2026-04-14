package model.payment.policy

import model.MovieReservationResult
import model.payment.DiscountPolicy
import model.payment.PayType

class PayTypeDiscount : DiscountPolicy {
    override fun apply(
        price: Int,
        reservations: List<MovieReservationResult.Success>,
        payType: PayType,
    ): Int = price - (price * payType.discountRate).toInt()
}
