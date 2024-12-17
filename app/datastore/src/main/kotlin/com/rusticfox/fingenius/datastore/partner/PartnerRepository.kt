package com.rusticfox.fingenius.datastore.partner

import com.mongodb.client.model.Filters
import com.mongodb.client.model.UpdateOptions
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoCollection
import com.rusticfox.fingenius.core.entities.Partner
import com.rusticfox.fingenius.core.exceptions.DatabaseException
import com.rusticfox.fingenius.core.exceptions.NotFoundException
import com.rusticfox.fingenius.core.ports.datastore.dto.PageRequest
import com.rusticfox.fingenius.datastore.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PartnerRepository(
    private val databaseClient: MongoCollection<PartnerModel>,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
): Repository<Partner, PartnerModel> {

    override suspend fun insert(data: Partner): PartnerModel = withContext(coroutineDispatcher) {
        val record = data.toPartnerModel()
        runCatching {
            databaseClient.insertOne(record)
        }
            .mapCatching {
                if (it.wasAcknowledged()) {
                    record
                } else {
                    throw Exception("Failed to insert partner record $data. Result: $it")
                }
            }
            .getOrElse {
                throw DatabaseException("insert failed $it", it)
            }
    }

    override suspend fun findById(id: String): PartnerModel? {
        TODO("Not yet implemented")
    }

    override suspend fun update(data: Partner) = withContext(coroutineDispatcher) {
        val updateResult = runCatching {
            val partnerToUpdate = data.toPartnerModel()
            val query = Filters.eq(PartnerModel::partnerId.name, data.partnerId.value)
            val updates = Updates.combine(

            )
            val options = UpdateOptions()
                .upsert(false)

            databaseClient.updateOne(query, updates, options)
        }
            .getOrElse { throw DatabaseException("update failed $it", it) }

        if (updateResult.matchedCount > 0) {
            println("Successfully updated partner ${data.partnerId}")
        } else {
            throw NotFoundException("No partner ${data.partnerId} to update")
        }
    }

    override suspend fun findAll(pageRequest: PageRequest): Collection<PartnerModel> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: String) {
        TODO("Not yet implemented")
    }
}
