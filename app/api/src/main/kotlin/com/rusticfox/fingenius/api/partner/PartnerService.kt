package com.rusticfox.fingenius.api.partner

import com.rusticfox.fingenius.api.dto.CreatePartnerRequestDto
import com.rusticfox.fingenius.api.dto.PartnerResponseDto
import com.rusticfox.fingenius.core.usecases.CreatePartnerUseCase

class PartnerService(
    private val createPartnerUseCase: CreatePartnerUseCase
) {

    suspend fun createPartner(payload: CreatePartnerRequestDto): PartnerResponseDto = run {
        val createdPartner = createPartnerUseCase(payload.toPartner())
        createdPartner.toPartnerResponseDto()
    }
}
