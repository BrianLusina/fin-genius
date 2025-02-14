package com.rusticfox.fingenius.domain.usecases.partner

import com.rusticfox.fingenius.core.entities.Partner
import com.rusticfox.fingenius.core.usecases.GetAllPartnersUseCase
import com.rusticfox.fingenius.domain.port.outbound.datastore.PartnerReadDataStorePort
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class GetAllPartnersUseCaseImpl(
    private val dataStore: PartnerReadDataStorePort,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
): GetAllPartnersUseCase {

    override suspend fun invoke(request: GetAllPartnersUseCase.GetAllPartnersRequest): Collection<Partner> = withContext(coroutineDispatcher) {
        runCatching {
            val partners = async {
                dataStore.findAll(request.type, request.status, request.openingBalance, request.pageRequest)
            }
            partners.await()
        }
            .getOrElse {
                // FIXME: log exception
                throw GetPartnersException("Failed to fetch partners $request", it)
            }
    }
}
