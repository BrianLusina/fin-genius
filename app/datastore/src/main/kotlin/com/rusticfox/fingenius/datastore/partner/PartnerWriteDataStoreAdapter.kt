package com.rusticfox.fingenius.datastore.partner

import com.rusticfox.fingenius.core.entities.Partner
import com.rusticfox.fingenius.domain.port.outbound.datastore.PartnerWriteDataStorePort
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PartnerWriteDataStoreAdapter(
    private val repository: PartnerRepository,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default
) : PartnerWriteDataStorePort {

    override suspend fun create(data: Partner): Partner = withContext(coroutineDispatcher) {
        runCatching { repository.insert(data) }
            .mapCatching { it.toPartner() }
            .getOrElse {
                throw Exception("Failed to create partner $data", it)
            }
    }

    override suspend fun update(data: Partner): Partner {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: String) {
        TODO("Not yet implemented")
    }
}
