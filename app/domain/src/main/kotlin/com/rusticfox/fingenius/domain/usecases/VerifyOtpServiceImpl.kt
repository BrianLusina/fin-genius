package com.rusticfox.fingenius.domain.usecases

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime

class VerifyOtpServiceImpl(private val dataStore: com.rusticfox.fingenius.core.ports.OtpDataStore) :
    com.rusticfox.fingenius.core.usecases.VerifyOtpService {

    override suspend fun execute(request: com.rusticfox.fingenius.core.entities.VerifyOtpCode): com.rusticfox.fingenius.core.entities.OtpVerificationStatus = runCatching {
        val otpCode = dataStore.getOtpCode(request.otpCode)
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toJavaLocalDateTime()
        val expiryDate = otpCode.expiryTime.toJavaLocalDateTime()
        val isOtpValid = now.isBefore(expiryDate)

        if (isOtpValid) {
            val usedOtpCode = otpCode.copy(used = true)
            dataStore.markOtpAsUsed(usedOtpCode)
            com.rusticfox.fingenius.core.entities.OtpVerificationStatus.VERIFIED
        } else {
            com.rusticfox.fingenius.core.entities.OtpVerificationStatus.CODE_EXPIRED
        }
    }
        .getOrElse {
            when (it) {
                is com.rusticfox.fingenius.core.exceptions.NotFoundException -> throw it
                else -> com.rusticfox.fingenius.core.entities.OtpVerificationStatus.FAILED_VERIFICATION
            }
        }
}
