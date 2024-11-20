package com.rusticfox.fingenius.datastore.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class InvoiceItemModel(
    val productName: String,
    val quantity: Int,
    @Contextual
    val rate: BigDecimal,
    @Contextual
    val valueOfSupplies: BigDecimal,
    @Contextual
    val salesTax: BigDecimal,
    @Contextual
    val netAmount: BigDecimal
)
