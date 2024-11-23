package com.rusticfox.fingenius.core.entities

import com.rusticfox.fingenius.core.values.Amount
import com.rusticfox.fingenius.core.values.Email
import com.rusticfox.fingenius.core.values.Name
import com.rusticfox.fingenius.core.values.PhoneNumber
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import java.math.BigDecimal
import java.util.*
import java.time.LocalDateTime as JavaLocalDateTime

/**
 * Representation of a Partner
 */
data class Partner(
    val type: PartnerType,
    val firstName: Name,
    val lastName: Name,
    val email: Email,
    val contactNo: PhoneNumber,
    val openingBalance: Amount = Amount(Currency.getInstance("KES"), BigDecimal.valueOf(0)),
    val address: String,
    val representative: PartnerRepresentative,
    val status: PartnerStatus = PartnerStatus.UNVERIFIED,
    val creationDate: LocalDateTime = JavaLocalDateTime.now().toKotlinLocalDateTime(),
    val updatedDate: LocalDateTime = creationDate,
    val deletedAt: LocalDateTime? = null,
    val partnerId: PartnerId = PartnerId(),
): Entity(creationDate=creationDate, updatedDate = updatedDate, deletedAt, entityId = partnerId)

data class PartnerRepresentative(
    val name: Name,
    val contact: PhoneNumber,
    val designation: String
)

enum class PartnerStatus {
    VERIFIED,
    UNVERIFIED,
    ACTIVE,
}

enum class PartnerType {
    VENDOR,
    CUSTOMER,
}