package com.rusticfox.fingenius.datastore.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames
import java.math.BigDecimal

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class ProductModel(
    @JsonNames("_id")
    val id: String? = null,
    val name: String,
    val coreCompany: String,
    @Contextual
    val rate: BigDecimal,
    val status: String,
    val taxExempted: Boolean,
    @Contextual
    val salesTax: BigDecimal,
    val notes: String,
)
