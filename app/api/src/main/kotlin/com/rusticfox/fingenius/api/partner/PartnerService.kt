package com.rusticfox.fingenius.api.partner

import com.rusticfox.fingenius.api.partner.dto.PartnerDto
import com.rusticfox.fingenius.api.partner.dto.PartnerResponseDto
import com.rusticfox.fingenius.core.entities.PartnerId
import com.rusticfox.fingenius.core.usecases.CreatePartnerUseCase
import com.rusticfox.fingenius.core.usecases.UpdatePartnerRequest
import com.rusticfox.fingenius.core.usecases.UpdatePartnerUseCase

class PartnerService(
    private val createPartnerUseCase: CreatePartnerUseCase,
    private val updatePartnerUseCase: UpdatePartnerUseCase
) {

    suspend fun createPartner(payload: PartnerDto): PartnerResponseDto = run {
        val createdPartner = createPartnerUseCase(payload.toPartner())
        createdPartner.toPartnerResponseDto()
    }

    suspend fun updatePartner(id: String, payload: PartnerDto): PartnerResponseDto = run {
        val partnerId = PartnerId(id)
        val partner = payload.toPartner()

        val updatedPartner = updatePartnerUseCase(
            UpdatePartnerRequest(
                partnerId = partnerId,
                type = partner.type,
                firstName = partner.firstName,
                lastName = partner.lastName,
                email = partner.email,
                contactNo = partner.contactNo,
                openingBalance = partner.openingBalance,
                address = partner.address,
                representative = partner.representative,
                status = partner.status
            )
        )

        updatedPartner.toPartnerResponseDto()
    }
}
