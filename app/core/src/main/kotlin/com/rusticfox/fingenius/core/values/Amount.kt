package com.rusticfox.fingenius.core.values

import java.math.BigDecimal
import java.util.Currency

/**
 * Represents an amount that contains the currency and the value of the amount
 * @param currency [Currency] Currency code of the amount
 * @param value [BigDecimal] the actual amount of the amount
 */
data class Amount(val currency: Currency, val value: BigDecimal) : Comparable<Amount> {

    operator fun minus(other: Amount): Amount {
        // ensure that the other amount is of the same currency
        isOfSameCurrency(other.currency)

        val diff = value.minus(other.value)

        return Amount(
            currency = currency,
            value = diff
        )
    }

    override fun compareTo(other: Amount): Int {
        // ensure that the other amount is of the same currency
        isOfSameCurrency(other.currency)

        return compareValuesBy(this, other) { it.value }
    }

    operator fun compareTo(amount: BigDecimal): Int {
        return when {
            amount == value -> 0
            value < amount -> -1
            value > amount -> 1
            else -> 0
        }
    }

    fun isZero(): Boolean {
        return value == BigDecimal.valueOf(0)
    }

    /**
     * Utility function to ensure that this amount is the same as the currency of the other amount
     */
    private fun isOfSameCurrency(other: Currency) {
        require(currency.currencyCode == other.currencyCode) {
            "Currency code of $other is not equal to $currency"
        }
    }

    override fun toString(): String {
        return "Amount(currency=$currency, value=$value)"
    }
}
