package com.rusticfox.fingenius.datastore.partner

import com.rusticfox.fingenius.core.entities.Partner
import com.rusticfox.fingenius.core.exceptions.DatabaseException
import com.rusticfox.fingenius.domain.port.outbound.datastore.PartnerWriteDataStorePort
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class PartnerWriteDataStoreAdapter(
    private val repository: PartnerRepository,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default
) : PartnerWriteDataStorePort {

    override suspend fun create(data: Partner): Partner = withContext(coroutineDispatcher) {
        runCatching {
            val result = async { repository.insert(data) }
            result.await()
        }
            .mapCatching { it.toPartner() }
            .getOrElse { throw DatabaseException("Failed to create partner $data", it) }
    }

    override suspend fun update(data: Partner): Partner = withContext(coroutineDispatcher) {
        runCatching {
            async { repository.update(data) }.await()
            data
        }
            .getOrElse { throw DatabaseException("Failed to update parter $data", it) }
    }

    override suspend fun delete(id: String) {
        TODO("Not yet implemented")
    }
}
