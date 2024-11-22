package com.rusticfox.fingenius.core.entities

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
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
    val representative: PartnerRepresentative,
    val creationDate: LocalDateTime = java.time.LocalDateTime.now().toKotlinLocalDateTime(),
    val updatedDate: LocalDateTime = creationDate,
    val deletedAt: LocalDateTime? = null,
    val partnerId: PartnerId = PartnerId(),
): Entity(creationDate=creationDate, updatedDate = updatedDate, deletedAt, entityId = partnerId)

data class PartnerRepresentative(
    val name: String,
    val contact: String,
    val designation: String
)
