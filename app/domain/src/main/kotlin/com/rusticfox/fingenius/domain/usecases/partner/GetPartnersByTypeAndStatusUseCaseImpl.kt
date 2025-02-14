package com.rusticfox.fingenius.domain.usecases.partner

import com.rusticfox.fingenius.core.entities.Partner
import com.rusticfox.fingenius.core.usecases.GetPartnersByTypeAndStatusUseCase
import com.rusticfox.fingenius.domain.port.outbound.datastore.PartnerReadDataStorePort
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class GetPartnersByTypeAndStatusUseCaseImpl(
    private val dataStore: PartnerReadDataStorePort,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
): GetPartnersByTypeAndStatusUseCase {

    override suspend fun invoke(request: GetPartnersByTypeAndStatusUseCase.GetPartnersByTypeAndStatusRequest): Collection<Partner> = withContext(coroutineDispatcher){
        runCatching {
            async {
                dataStore.findAllByTypeAndStatus(request.type, request.status, request.pageRequest)
            }
                .await()
        }
            .getOrElse {
                // FIXME: log exception
                throw GetPartnersException("Failed to fetch partners with request: $request", it)
            }
    }
}
