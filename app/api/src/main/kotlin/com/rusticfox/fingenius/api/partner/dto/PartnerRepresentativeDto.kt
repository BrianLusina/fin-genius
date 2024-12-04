package com.rusticfox.fingenius.api.partner.dto

import kotlinx.serialization.Serializable

@Serializable
data class PartnerRepresentativeResponseDto(
    val name: String,
    val contact: String,
    val designation: String
)

@Serializable
data class CreatePartnerRepresentativeDto(
    val name: String,
    val contact: String,
    val designation: String
)

@Serializable
data class UpdatePartnerRepresentativeDto(
    val name: String? = null,
    val contact: String? = null,
    val designation: String? = null
)
