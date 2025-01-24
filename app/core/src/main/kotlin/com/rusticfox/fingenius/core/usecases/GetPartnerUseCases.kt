package com.rusticfox.fingenius.core.usecases

import com.rusticfox.fingenius.core.entities.PartnerId
import com.rusticfox.fingenius.core.entities.Partner
import com.rusticfox.fingenius.core.entities.PartnerStatus
import com.rusticfox.fingenius.core.entities.PartnerType
import com.rusticfox.fingenius.core.interactor.UseCase
import com.rusticfox.fingenius.core.ports.datastore.dto.PageRequest
import com.rusticfox.fingenius.core.values.Amount

/**
 * Retrieves a partner given their ID
 */
interface GetPartnerByIdUseCase: UseCase<PartnerId, Partner?> {
    override suspend operator fun invoke(request: PartnerId): Partner?
}

/**
 * Get partner by type use case
 */
interface GetPartnersByTypeUseCase: UseCase<GetPartnersByTypeUseCase.GetPartnersByTypeUseCaseRequest, Collection<Partner>> {
    data class GetPartnersByTypeUseCaseRequest(
        val type: PartnerType,
        val pageRequest: PageRequest = PageRequest()
    )

    override suspend operator fun invoke(request: GetPartnersByTypeUseCaseRequest): List<Partner>
}

/**
 * Get partner by type use case
 */
interface GetPartnersByTypeAndStatusUseCase: UseCase<GetPartnersByTypeAndStatusUseCase.GetPartnersByTypAndStatusRequest, Collection<Partner>> {
    data class GetPartnersByTypAndStatusRequest(
        val type: PartnerType,
        val status: PartnerStatus,
        val pageRequest: PageRequest = PageRequest()
    )

    override suspend operator fun invoke(request: GetPartnersByTypAndStatusRequest): Collection<Partner>
}

/**
 * Get all partners
 */
interface GetAllPartnersUseCase: UseCase<GetAllPartnersUseCase.GetAllPartnersRequest, Collection<Partner>> {
    data class GetAllPartnersRequest(
        val type: PartnerType ?= null,
        val status: PartnerStatus ?= null,
        val openingBalance: Amount? = null,
        val pageRequest: PageRequest = PageRequest()
    )

    override suspend operator fun invoke(request: GetAllPartnersRequest): Collection<Partner>
}
