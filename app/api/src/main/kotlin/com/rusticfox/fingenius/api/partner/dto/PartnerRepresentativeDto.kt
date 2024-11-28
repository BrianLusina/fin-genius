package com.rusticfox.fingenius.api.partner.dto

import kotlinx.serialization.Serializable

@Serializable
data class PartnerRepresentativeDto(
    val name: String,
    val contact: String,
    val designation: String
)
