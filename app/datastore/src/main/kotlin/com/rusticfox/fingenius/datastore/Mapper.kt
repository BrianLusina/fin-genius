package com.rusticfox.fingenius.datastore

import com.rusticfox.fingenius.core.entities.OtpCode
import com.rusticfox.fingenius.core.entities.UserId
import com.rusticfox.fingenius.datastore.models.OtpEntity
import kotlinx.datetime.toKotlinLocalDateTime

fun mapModelToEntity(otpEntity: OtpEntity): com.rusticfox.fingenius.core.entities.OtpCode {
    return com.rusticfox.fingenius.core.entities.OtpCode(
        code = otpEntity.code,
        userId = com.rusticfox.fingenius.core.entities.UserId(otpEntity.userId),
        expiryTime = otpEntity.expiryTime.toKotlinLocalDateTime(),
        used = otpEntity.used
    )
}
