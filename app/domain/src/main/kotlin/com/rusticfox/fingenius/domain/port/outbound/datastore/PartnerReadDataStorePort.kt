package com.rusticfox.fingenius.domain.port.outbound.datastore

import com.rusticfox.fingenius.core.entities.Partner
import com.rusticfox.fingenius.core.entities.PartnerStatus
import com.rusticfox.fingenius.core.entities.PartnerType
import com.rusticfox.fingenius.core.ports.datastore.ReadDataStore
import com.rusticfox.fingenius.core.ports.datastore.dto.PageRequest
import com.rusticfox.fingenius.core.values.Amount

interface PartnerReadDataStorePort: ReadDataStore<Partner> {
    suspend fun findAll(
        type: PartnerType?,
        status: PartnerStatus?,
        openingBalance: Amount? = null,
        pageRequest: PageRequest = PageRequest()
    ): Collection<Partner>

    suspend fun findAllByType(type: PartnerType, pageRequest: PageRequest = PageRequest()): Collection<Partner>

    suspend fun findAllByTypeAndStatus(
        type: PartnerType,
        status: PartnerStatus,
        pageRequest: PageRequest = PageRequest()
    ): Collection<Partner>
}
