package com.rusticfox.fingenius.api.dto

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class CreatePartnerRequestDto(
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
)
