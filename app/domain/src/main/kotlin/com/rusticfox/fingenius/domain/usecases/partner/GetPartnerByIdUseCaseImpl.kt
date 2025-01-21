package com.rusticfox.fingenius.domain.usecases.partner

import com.rusticfox.fingenius.core.entities.Partner
import com.rusticfox.fingenius.core.entities.PartnerId
import com.rusticfox.fingenius.core.exceptions.NotFoundException
import com.rusticfox.fingenius.core.usecases.GetPartnerByIdUseCase
import com.rusticfox.fingenius.domain.port.outbound.datastore.PartnerReadDataStorePort
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class GetPartnerByIdUseCaseImpl(
    private val dataStore: PartnerReadDataStorePort,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
): GetPartnerByIdUseCase {

    override suspend fun invoke(request: PartnerId): Partner? = withContext(coroutineDispatcher) {
        runCatching {
            val partner = async {
                dataStore.findById(request.id) ?: throw NotFoundException("Failed to fetch partner $request")
            }
            partner.await()
        }
            .getOrElse {
                // FIXME: log exception
                throw GetPartnerByIdException("Failed to fetch partner $request", it)
            }
    }
}
