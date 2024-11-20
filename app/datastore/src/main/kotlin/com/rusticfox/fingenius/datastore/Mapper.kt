package com.rusticfox.fingenius.datastore

import com.rusticfox.fingenius.datastore.models.InvoiceItemModel
import kotlinx.datetime.toKotlinLocalDateTime

fun mapModelToEntity(invoiceItemModel: InvoiceItemModel): com.rusticfox.fingenius.core.entities.OtpCode {
    return com.rusticfox.fingenius.core.entities.OtpCode(
        code = invoiceItemModel.code,
        userId = com.rusticfox.fingenius.core.entities.UserId(invoiceItemModel.userId),
        expiryTime = invoiceItemModel.expiryTime.toKotlinLocalDateTime(),
        used = invoiceItemModel.used
    )
}
