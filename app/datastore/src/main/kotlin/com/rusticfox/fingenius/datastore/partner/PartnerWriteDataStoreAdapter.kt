package com.rusticfox.fingenius.datastore.partner

import com.rusticfox.fingenius.core.entities.Partner
import com.rusticfox.fingenius.core.ports.datastore.dto.PageRequest
import com.rusticfox.fingenius.domain.port.outbound.datastore.PartnerReadDataStorePort
import com.rusticfox.fingenius.domain.port.outbound.datastore.PartnerWriteDataStorePort
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class PartnerWriteDataStoreAdapter(
    private val repository: PartnerRepository,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default
) : PartnerWriteDataStorePort {
    override suspend fun create(data: Partner): Partner {
        TODO("Not yet implemented")
    }

    override suspend fun update(data: Partner): Partner {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: String) {
        TODO("Not yet implemented")
    }
}
