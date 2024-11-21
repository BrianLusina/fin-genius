package com.rusticfox.fingenius.core.entities

import java.math.BigDecimal

/**
 * Representation of a Partner
 */
data class Partner(
    val type: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val status: String,
    val contactNo: String,
    val openingBalance: BigDecimal,
    val address: String,
    val repName: String,
    val repContact: String,
    val repDesignation: String
)
