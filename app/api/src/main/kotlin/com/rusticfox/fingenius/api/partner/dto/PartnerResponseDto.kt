package com.rusticfox.fingenius.api.partner.dto

import com.rusticfox.fingenius.api.dto.BigDecimalJson
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class PartnerResponseDto(
    val id: String,
    val type: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val status: String,
    val contactNo: String,
    @Contextual
    val openingBalance: BigDecimalJson,
    val address: String,
    @Contextual
    val representative: PartnerRepresentativeResponseDto,
)
