package com.rusticfox.fingenius.api

import com.rusticfox.fingenius.api.dto.OtpRequestDto
import com.rusticfox.fingenius.api.dto.OtpVerifyDto
import com.rusticfox.fingenius.core.entities.OtpCode
import com.rusticfox.fingenius.core.entities.OtpVerificationStatus
import com.rusticfox.fingenius.core.entities.UserId
import com.rusticfox.fingenius.core.entities.VerifyOtpCode
import com.rusticfox.fingenius.core.services.CreateOtpService
import com.rusticfox.fingenius.core.services.VerifyOtpService

class OtpService(
    private val createOtpService: com.rusticfox.fingenius.core.services.CreateOtpService,
    private val verifyOtpService: com.rusticfox.fingenius.core.services.VerifyOtpService
) {

    /**
     * Verifies user OTP code
     * @return [OtpVerificationStatus]
     */
    suspend fun verifyOtp(verifyOtpDto: OtpVerifyDto): com.rusticfox.fingenius.core.entities.OtpVerificationStatus = run {
        verifyOtpService.execute(
            com.rusticfox.fingenius.core.entities.VerifyOtpCode(
                otpCode = verifyOtpDto.code, userId = com.rusticfox.fingenius.core.entities.UserId(verifyOtpDto.userId)
            )
        )
    }

    suspend fun generateOtp(otpRequestDto: OtpRequestDto): com.rusticfox.fingenius.core.entities.OtpCode = run {
        createOtpService.execute(com.rusticfox.fingenius.core.entities.UserId(otpRequestDto.userId))
    }
}
