package com.rusticfox.fingenius.domain.usecases.partner

import com.rusticfox.fingenius.core.entities.Partner
import com.rusticfox.fingenius.core.usecases.CreatePartnerUseCase
import com.rusticfox.fingenius.domain.port.outbound.datastore.PartnerDataStorePort

/**
 * Creates partner code for given user
 */
class CreatePartnerUseCaseImpl(
    private val dataStore: PartnerDataStorePort
) : CreatePartnerUseCase {

    override suspend fun invoke(request: Partner) {
        run {
            dataStore.create(request)
        }
    }
}
