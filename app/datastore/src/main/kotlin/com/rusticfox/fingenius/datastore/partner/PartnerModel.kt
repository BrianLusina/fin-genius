package com.rusticfox.fingenius.datastore.partner

import com.rusticfox.fingenius.datastore.models.BaseModel
import kotlinx.serialization.Contextual
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames
import java.math.BigDecimal

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class PartnerModel(
    val partnerId: String? = null,
    @JsonNames("_id")
    val id: String? = null,
    val type: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val status: String,
    val contactNo: String,
    @Contextual
    val openingBalance: BigDecimal,
    val address: String,
    val repName: String,
    val repContact: String,
    val repDesignation: String
): BaseModel()
