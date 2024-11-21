package com.rusticfox.fingenius.datastore.partner

import com.rusticfox.fingenius.core.entities.Partner
import com.rusticfox.fingenius.core.ports.datastore.dto.PageRequest
import com.rusticfox.fingenius.domain.port.outbound.datastore.PartnerReadDataStorePort
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class PartnerReadDataStoreAdapter(
    private val repository: PartnerRepository,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default
) : PartnerReadDataStorePort {

    override suspend fun findById(id: String): Partner? {
        TODO("Not yet implemented")
    }

    override suspend fun findAll(pageRequest: PageRequest): Collection<Partner> {
        TODO("Not yet implemented")
    }

}
