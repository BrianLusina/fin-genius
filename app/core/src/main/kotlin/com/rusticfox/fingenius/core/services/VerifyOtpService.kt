package com.rusticfox.fingenius.core.services

import com.rusticfox.fingenius.core.entities.OtpVerificationStatus
import com.rusticfox.fingenius.core.entities.VerifyOtpCode
import com.rusticfox.fingenius.core.interactor.UseCase

interface VerifyOtpService :
    com.rusticfox.fingenius.core.interactor.UseCase<com.rusticfox.fingenius.core.entities.VerifyOtpCode, com.rusticfox.fingenius.core.entities.OtpVerificationStatus> {
    override suspend fun execute(request: com.rusticfox.fingenius.core.entities.VerifyOtpCode): com.rusticfox.fingenius.core.entities.OtpVerificationStatus
}
