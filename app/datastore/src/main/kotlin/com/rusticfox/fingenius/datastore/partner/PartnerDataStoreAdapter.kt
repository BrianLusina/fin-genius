package com.rusticfox.fingenius.datastore.partner

import com.rusticfox.fingenius.domain.port.outbound.datastore.PartnerDataStorePort
import com.rusticfox.fingenius.domain.port.outbound.datastore.PartnerReadDataStorePort
import com.rusticfox.fingenius.domain.port.outbound.datastore.PartnerWriteDataStorePort

class PartnerDataStoreAdapter(
    private val repository: PartnerRepository,
    private val readStore: PartnerReadDataStorePort,
    private val writeStore: PartnerWriteDataStorePort
) : PartnerDataStorePort, PartnerReadDataStorePort by readStore, PartnerWriteDataStorePort by writeStore {

    override suspend fun exists(id: String): Boolean {
        return repository.findById(id) != null
    }
}
