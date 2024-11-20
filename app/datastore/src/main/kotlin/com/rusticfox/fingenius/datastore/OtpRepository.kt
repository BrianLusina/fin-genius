package com.rusticfox.fingenius.datastore

import com.rusticfox.fingenius.datastore.DatabaseFactory.dbQuery
import com.rusticfox.fingenius.datastore.models.InvoiceItemModel
import kotlinx.datetime.toJavaLocalDateTime
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

object OtpRepository {

    suspend fun insert(otpCode: com.rusticfox.fingenius.core.entities.OtpCode): InvoiceItemModel = dbQuery {
        InvoiceItemModel.new {
            code = otpCode.code
            userId = otpCode.userId.value
            expiryTime = otpCode.expiryTime.toJavaLocalDateTime()
            used = otpCode.used
        }
    }

    suspend fun findAll(): Collection<InvoiceItemModel> = dbQuery {
        transaction { InvoiceItemModel.all().toList() }
    }

    suspend fun findAllByUserId(userId: String): Collection<InvoiceItemModel> = dbQuery {
        transaction { InvoiceItemModel.find { OtpTable.userId eq userId }.toList() }
    }

    suspend fun findByCode(code: String): InvoiceItemModel? = dbQuery {
        InvoiceItemModel.find {
            OtpTable.code eq code
        }
            .firstOrNull()
    }

    suspend fun update(invoiceItemModel: InvoiceItemModel) = dbQuery {
        OtpTable.update({ OtpTable.code eq invoiceItemModel.code }) {
            it[used] = invoiceItemModel.used
            it[userId] = invoiceItemModel.userId
            it[expiryTime] = invoiceItemModel.expiryTime
        }
    }

    suspend fun markAsUsed(code: String) = dbQuery {
        OtpTable.update({ OtpTable.code eq code }) {
            it[used] = true
        }
    }
}
