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
data class InvoiceModel(
    @JsonNames("_id")
    val id: String? = null,
    val type: String,
    val invoiceDate: LocalDateTime,
    val dueDate: LocalDateTime,
    val invoiceNo: String,
    val partnerId: String,
    val creditTerm: String,
    val reference: String,
    @Contextual
    val invoiceTotal: BigDecimal,
    val invoiceItems: List<InvoiceItemModel>
)

@Serializable
data class InvoiceJsonModel(
    val partner: PartnerModel? = null,
    val invoice: InvoiceModel
)
