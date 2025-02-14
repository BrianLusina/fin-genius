package com.rusticfox.fingenius.domain.usecases.partner

import com.rusticfox.fingenius.core.entities.Partner
import com.rusticfox.fingenius.core.usecases.GetPartnersByTypeUseCase
import com.rusticfox.fingenius.domain.port.outbound.datastore.PartnerReadDataStorePort
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class GetPartnersByTypeUseCaseImpl(
    private val dataStore: PartnerReadDataStorePort,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
): GetPartnersByTypeUseCase {

    override suspend fun invoke(request: GetPartnersByTypeUseCase.GetPartnersByTypeUseCaseRequest): Collection<Partner> = withContext(coroutineDispatcher) {
        runCatching {
            async {
                dataStore.findAllByType(request.type, request.pageRequest)
            }
                .await()
        }
            .getOrElse {
                // FIXME: log exception
                throw GetPartnersException("Failed to fetch partners with request: $request", it)
            }
    }
}
