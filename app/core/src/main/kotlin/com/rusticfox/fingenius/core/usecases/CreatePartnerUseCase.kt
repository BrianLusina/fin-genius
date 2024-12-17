package com.rusticfox.fingenius.core.usecases

import com.rusticfox.fingenius.core.entities.Partner
import com.rusticfox.fingenius.core.interactor.UseCase

/**
 * Creates a new partner
 */
interface CreatePartnerUseCase: UseCase<Partner, Partner> {
    override suspend operator fun invoke(request: Partner): Partner
}
