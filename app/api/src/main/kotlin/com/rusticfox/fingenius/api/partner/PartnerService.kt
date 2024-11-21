package com.rusticfox.fingenius.api.partner

import com.rusticfox.fingenius.api.dto.CreatePartnerRequestDto
import com.rusticfox.fingenius.api.dto.OtpVerifyDto
import com.rusticfox.fingenius.core.entities.OtpVerificationStatus
import com.rusticfox.fingenius.core.entities.Partner
import com.rusticfox.fingenius.core.entities.UserId
import com.rusticfox.fingenius.core.entities.VerifyOtpCode
import com.rusticfox.fingenius.core.usecases.CreatePartnerUseCase
import com.rusticfox.fingenius.core.usecases.VerifyOtpService

class PartnerService(
    private val createPartnerUseCase: CreatePartnerUseCase,
    private val verifyOtpService: VerifyOtpService
) {

    suspend fun createPartner(payload: CreatePartnerRequestDto) = run {
        val partner = Partner(
            type = payload.type,
            firstName = payload.firstName,
            lastName = payload.lastName,
            email = payload.email,
            status = payload.status,
            contactNo = payload.contactNo,
            openingBalance = payload.openingBalance,
            address = payload.address,
            repName = payload.repName,
            repContact = payload.repContact,
            repDesignation = payload.repDesignation
        )
        createPartnerUseCase(partner)
    }

    /**
     * Verifies user OTP code
     * @return [OtpVerificationStatus]
     */
    suspend fun verifyOtp(verifyOtpDto: OtpVerifyDto): OtpVerificationStatus = run {
        verifyOtpService.execute(VerifyOtpCode(
                otpCode = verifyOtpDto.code, userId = UserId(verifyOtpDto.userId)
            )
        )
    }
}
