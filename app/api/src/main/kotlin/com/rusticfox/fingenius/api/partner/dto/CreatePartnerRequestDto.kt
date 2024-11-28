package com.rusticfox.fingenius.api.partner.dto

import com.rusticfox.fingenius.api.dto.BigDecimalJson
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

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
    val openingBalance: BigDecimalJson = BigDecimalJson.valueOf(0)
)
