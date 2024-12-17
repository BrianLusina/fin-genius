package com.rusticfox.fingenius.domain.usecases.partner.create

import com.rusticfox.fingenius.core.entities.Partner
import com.rusticfox.fingenius.core.usecases.CreatePartnerUseCase
import com.rusticfox.fingenius.domain.port.outbound.datastore.PartnerDataStorePort

/**
 * Creates partner for given user
 */
class CreatePartnerUseCaseImpl(private val dataStore: PartnerDataStorePort): CreatePartnerUseCase {

    override suspend fun invoke(request: Partner): Partner {
        return runCatching { dataStore.create(request) }
            .getOrElse {
                throw CreatePartnerException("Failed to create new partner $request", it)
            }
    }
}
