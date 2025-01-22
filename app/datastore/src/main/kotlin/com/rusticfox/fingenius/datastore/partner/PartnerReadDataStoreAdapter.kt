package com.rusticfox.fingenius.datastore.partner

import com.rusticfox.fingenius.core.entities.Partner
import com.rusticfox.fingenius.core.exceptions.DatabaseException
import com.rusticfox.fingenius.core.exceptions.NotFoundException
import com.rusticfox.fingenius.core.ports.datastore.dto.PageRequest
import com.rusticfox.fingenius.domain.port.outbound.datastore.PartnerReadDataStorePort
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class PartnerReadDataStoreAdapter(
    private val repository: PartnerRepository,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : PartnerReadDataStorePort {

    override suspend fun findById(id: String): Partner? = withContext(coroutineDispatcher) {
        runCatching {
            val result = async { repository.findById(id) }
            result.await()
        }
            .mapCatching {
                checkNotNull(it) { throw NotFoundException("Partner $id does not exist") }
                it.toPartner()
            }
            .getOrElse { throw DatabaseException("Failed to retrieve partner $id", it) }
    }

    override suspend fun findAll(pageRequest: PageRequest): Collection<Partner> {
        TODO("Not yet implemented")
    }
}
