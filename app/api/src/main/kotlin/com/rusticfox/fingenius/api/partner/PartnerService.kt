package com.rusticfox.fingenius.api.partner

import com.rusticfox.fingenius.api.partner.dto.PartnerDto
import com.rusticfox.fingenius.api.partner.dto.PartnerResponseDto
import com.rusticfox.fingenius.core.entities.PartnerId
import com.rusticfox.fingenius.core.entities.PartnerStatus
import com.rusticfox.fingenius.core.entities.PartnerType
import com.rusticfox.fingenius.core.exceptions.NotFoundException
import com.rusticfox.fingenius.core.ports.datastore.dto.PageRequest
import com.rusticfox.fingenius.core.usecases.CreatePartnerUseCase
import com.rusticfox.fingenius.core.usecases.GetAllPartnersUseCase
import com.rusticfox.fingenius.core.usecases.GetPartnerByIdUseCase
import com.rusticfox.fingenius.core.usecases.GetPartnersByTypeAndStatusUseCase
import com.rusticfox.fingenius.core.usecases.GetPartnersByTypeUseCase
import com.rusticfox.fingenius.core.usecases.UpdatePartnerRequest
import com.rusticfox.fingenius.core.usecases.UpdatePartnerUseCase
import com.rusticfox.fingenius.core.values.Amount

class PartnerService(
    private val createPartnerUseCase: CreatePartnerUseCase,
    private val updatePartnerUseCase: UpdatePartnerUseCase,
    private val getPartnerByIdUseCase: GetPartnerByIdUseCase,
    private val getPartnersByTypeUseCase: GetPartnersByTypeUseCase,
    private val getPartnersByTypeAndStatusUseCase: GetPartnersByTypeAndStatusUseCase,
    private val getAllPartnersUseCase: GetAllPartnersUseCase,
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

    suspend fun getPartnerById(id: String): PartnerResponseDto = run {
        val partnerId = PartnerId(id)
        val existingPartner = getPartnerByIdUseCase(partnerId) ?: throw NotFoundException("Partner with ID $id does not exist")
        existingPartner.toPartnerResponseDto()
    }

    suspend fun getAllPartners(type: String? = null, status: String? = null, pageRequest: PageRequest): List<PartnerResponseDto> = run {
        val partnerType = if (type !=null ) PartnerType.valueOf(type) else null
        val partnerStatus = if (status != null) PartnerStatus.valueOf(status) else null

        val partners = when {
            partnerType != null && partnerStatus == null -> {
                getPartnersByTypeUseCase(
                    GetPartnersByTypeUseCase.GetPartnersByTypeUseCaseRequest(
                        type = partnerType,
                        pageRequest = pageRequest
                    )
                )
            }

            partnerType != null && partnerStatus != null -> {
                getPartnersByTypeAndStatusUseCase(
                    GetPartnersByTypeAndStatusUseCase.GetPartnersByTypAndStatusRequest(
                        type = partnerType,
                        status = partnerStatus,
                        pageRequest = pageRequest
                    )
                )
            }

            else -> {
                getAllPartnersUseCase(
                    GetAllPartnersUseCase.GetAllPartnersRequest(
                        type = partnerType,
                        status = partnerStatus,
                        pageRequest = pageRequest
                    )
                )
            }
        }
        partners.map { it.toPartnerResponseDto() }
    }
}
