package com.rusticfox.fingenius.core.usecases

interface VerifyOtpService :
    com.rusticfox.fingenius.core.interactor.UseCase<com.rusticfox.fingenius.core.entities.VerifyOtpCode, com.rusticfox.fingenius.core.entities.OtpVerificationStatus> {
    override suspend fun execute(request: com.rusticfox.fingenius.core.entities.VerifyOtpCode): com.rusticfox.fingenius.core.entities.OtpVerificationStatus
}
