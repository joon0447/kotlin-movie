package model.payment

import model.payment.DiscountPolicyObject.CASH_DISCOUNT_RATE
import model.payment.DiscountPolicyObject.CREDIT_CARD_DISCOUNT_RATE

enum class PayType(
    val id: Int,
    val discountRate: Double,
) {
    CREDIT_CARD(
        id = 1,
        discountRate = CREDIT_CARD_DISCOUNT_RATE,
    ),
    CASH(
        id = 2,
        discountRate = CASH_DISCOUNT_RATE,
    ),
    ;

    companion object {
        fun fromId(id: Int): PayType =
            entries.firstOrNull { it.id == id }
                ?: throw IllegalArgumentException(Message.INVALID_PAY_TYPE)
    }
}
