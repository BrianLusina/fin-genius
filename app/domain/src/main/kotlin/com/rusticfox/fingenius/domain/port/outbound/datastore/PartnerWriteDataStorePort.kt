package com.rusticfox.fingenius.domain.port.outbound.datastore

import com.rusticfox.fingenius.core.entities.Partner
import com.rusticfox.fingenius.core.ports.datastore.WriteDatastore

interface PartnerWriteDataStorePort: WriteDatastore<Partner>
