package com.rusticfox.fingenius.datastore.partner

import com.rusticfox.fingenius.core.entities.PartnerStatus
import com.rusticfox.fingenius.core.entities.PartnerType
import com.rusticfox.fingenius.datastore.models.BaseModel
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class PartnerModel(
    val partnerId: String? = null,
    val type: PartnerType,
    val firstName: String,
    val lastName: String,
    val email: String,
    val status: PartnerStatus,
    val contactNo: String,
    @Contextual
    val openingBalance: BigDecimal,
    val currency: String,
    val address: String,
    val representative: PartnerRepresentativeModel
): BaseModel()
