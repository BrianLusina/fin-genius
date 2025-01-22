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
interface GetPartnersByTypeUseCase: UseCase<PartnerType, List<Partner>> {
    override suspend operator fun invoke(request: PartnerType): List<Partner>
}

/**
 * Get partner by type use case
 */
interface GetPartnersByTypeAndStatusUseCase: UseCase<GetPartnersByTypeAndStatusUseCase.GetPartnersByTypAndStatusRequest, List<Partner>> {
    data class GetPartnersByTypAndStatusRequest(
        val type: PartnerType,
        val status: PartnerStatus
    )

    override suspend operator fun invoke(request: GetPartnersByTypAndStatusRequest): List<Partner>
}

/**
 * Get all partners
 */
interface GetAllPartnersUseCase: UseCase<GetAllPartnersUseCase.GetAllPartnersRequest, List<Partner>> {
    data class GetAllPartnersRequest(
        val type: PartnerType,
        val status: PartnerStatus,
        val openingBalance: Amount,
        val pageRequest: PageRequest
    )

    override suspend operator fun invoke(request: GetAllPartnersRequest): List<Partner>
}
