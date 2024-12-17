package com.rusticfox.fingenius.domain.usecases.partner.edit

import com.rusticfox.fingenius.core.entities.Partner
import com.rusticfox.fingenius.core.usecases.UpdatePartnerRequest
import com.rusticfox.fingenius.core.usecases.UpdatePartnerUseCase
import com.rusticfox.fingenius.domain.port.outbound.datastore.PartnerDataStorePort
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class UpdatePartnerUseCaseImpl(
    private val dataStore: PartnerDataStorePort,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default
): UpdatePartnerUseCase {

    override suspend fun invoke(request: UpdatePartnerRequest): Partner  = withContext(coroutineDispatcher) {
        val maybeExistingPartner = runCatching {
            val partner = async { dataStore.findById(request.partnerId.id) }
            partner.await()
        }
            .getOrElse { throw EditPartnerException("Failed to fetch partner ${request.partnerId}", it) }

        checkNotNull(maybeExistingPartner) {
            throw EditPartnerException("Failed to edit partner ${request.partnerId} as they do not exist")
        }

        runCatching {
            val updatedPartner = maybeExistingPartner.copy(
                type = request.type ?: maybeExistingPartner.type,
                firstName = request.firstName ?: maybeExistingPartner.firstName,
                lastName = request.lastName ?: maybeExistingPartner.lastName,
                email = request.email ?: maybeExistingPartner.email,
                contactNo = request.contactNo ?: maybeExistingPartner.contactNo,
                openingBalance = request.openingBalance ?: maybeExistingPartner.openingBalance,
                address = request.address ?: maybeExistingPartner.address,
                representative = request.representative ?: maybeExistingPartner.representative,
                status = request.status ?: maybeExistingPartner.status,
            )

            async { dataStore.update(updatedPartner) }.await()
        }
            .getOrElse { throw EditPartnerException("Failed to edit partner ${request.partnerId}", it) }
    }
}
