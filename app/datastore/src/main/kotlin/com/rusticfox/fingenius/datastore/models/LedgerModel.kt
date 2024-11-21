package com.rusticfox.fingenius.datastore.models

import com.rusticfox.fingenius.datastore.partner.PartnerModel
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class LedgerItemModel(
    val date: LocalDateTime,
    val instrumentNo: String,
    val reference: String,
    val description: String,
    val quantity: Int,
    @Contextual
    val debit: BigDecimal,
    @Contextual
    val credit: BigDecimal,
    @Contextual
    val balance: BigDecimal
)

@Serializable
data class LedgerJson(
    val partner: PartnerModel? = null,
    val ledgerItems: List<LedgerItemModel>
)