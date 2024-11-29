package com.rusticfox.fingenius.datastore.partner

import kotlinx.serialization.Serializable

@Serializable
data class PartnerRepresentativeModel(
    val name: String,
    val contact: String,
    val designation: String
)
