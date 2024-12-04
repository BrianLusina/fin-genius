package com.rusticfox.fingenius.api.partner

import com.rusticfox.fingenius.api.partner.dto.PartnerDto
import com.rusticfox.fingenius.api.partner.dto.PartnerResponseDto
import com.rusticfox.fingenius.core.usecases.CreatePartnerUseCase

class PartnerService(
    private val createPartnerUseCase: CreatePartnerUseCase
) {

    suspend fun createPartner(payload: PartnerDto): PartnerResponseDto = run {
        val createdPartner = createPartnerUseCase(payload.toPartner())
        createdPartner.toPartnerResponseDto()
    }

    suspend fun updatePartner(payload: PartnerDto): PartnerResponseDto = run {
        val createdPartner = createPartnerUseCase(payload.toPartner())
        createdPartner.toPartnerResponseDto()
    }
}
