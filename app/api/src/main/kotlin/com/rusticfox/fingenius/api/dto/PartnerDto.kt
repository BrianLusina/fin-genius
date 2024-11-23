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
    val contactNo: String,
    val address: String,
    @Contextual
    val representative: PartnerRepresentativeDto,
    @Contextual
    val openingBalance: BigDecimal = BigDecimal.valueOf(0)
)

@Serializable
data class PartnerResponseDto(
    val type: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val status: String,
    val contactNo: String,
    @Contextual
    val openingBalance: BigDecimal,
    val address: String,
    @Contextual
    val representative: PartnerRepresentativeDto,
)

@Serializable
data class PartnerRepresentativeDto(
    val name: String,
    val contact: String,
    val designation: String
)