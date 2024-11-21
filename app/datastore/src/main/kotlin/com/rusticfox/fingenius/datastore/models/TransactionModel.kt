package com.rusticfox.fingenius.datastore.models

import com.rusticfox.fingenius.datastore.partner.PartnerModel
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames
import java.math.BigDecimal

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class TransactionModel(
    @JsonNames("_id")
    val id: String? = null,
    val voucherNo: String,
    val type: String,
    val date: LocalDateTime,
    val paymentTo: String,
    val receiptFrom: String,
    @Contextual
    val amount: BigDecimal,
    val reference: String
)


@Serializable
data class TransactionJsonModel(
    val partner: PartnerModel? = null,
    val transaction: TransactionModel
)
