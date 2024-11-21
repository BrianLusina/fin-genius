package com.rusticfox.fingenius.datastore.partner

import com.mongodb.kotlin.client.coroutine.MongoCollection
import com.rusticfox.fingenius.core.entities.Partner
import com.rusticfox.fingenius.core.ports.datastore.dto.PageRequest
import com.rusticfox.fingenius.datastore.Repository

class PartnerRepository(private val databaseClient: MongoCollection<PartnerModel>): Repository<Partner, PartnerModel> {
    override suspend fun insert(data: Partner): PartnerModel {
        TODO("Not yet implemented")
    }

    override suspend fun findById(id: String): PartnerModel? {
        TODO("Not yet implemented")
    }

    override suspend fun findAll(pageRequest: PageRequest): Collection<PartnerModel> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: String) {
        TODO("Not yet implemented")
    }

    override suspend fun update(data: Partner) {
        TODO("Not yet implemented")
    }
}
