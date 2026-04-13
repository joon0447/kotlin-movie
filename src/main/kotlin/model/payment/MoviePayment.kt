package model.payment

import model.MovieReservationResult

class MoviePayment(
    val reservations: List<MovieReservationResult.Success>,
    private val policies: List<DiscountPolicy>,
) {
    val originalPrice: Int = reservations.sumOf { it.seat.price }

    fun getReceipt(
        payType: PayType,
        point: Int,
    ): Receipt {
        val finalPrice =
            getFinalPrice(
                payType = payType,
            )
        return Receipt(finalPrice = finalPrice, usedPoint = point)
    }

    fun getFinalPrice(payType: PayType): Int =
        policies.fold(originalPrice) { acc, policy ->
            policy.apply(acc, reservations, payType)
        }
}
