package com.rusticfox.fingenius.core.usecases

import com.rusticfox.fingenius.core.entities.PartnerId
import com.rusticfox.fingenius.core.entities.Partner
import com.rusticfox.fingenius.core.interactor.UseCase

/**
 * Retrieves a partner given their ID
 */
interface GetPartnerByIdUseCase: UseCase<PartnerId, Partner?> {
    override suspend operator fun invoke(request: PartnerId): Partner?
}
