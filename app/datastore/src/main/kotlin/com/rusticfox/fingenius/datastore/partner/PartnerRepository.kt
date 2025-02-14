package com.rusticfox.fingenius.datastore.partner

import com.mongodb.client.model.BsonField
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Sorts
import com.mongodb.client.model.UpdateOptions
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoCollection
import com.rusticfox.fingenius.core.entities.Partner
import com.rusticfox.fingenius.core.entities.PartnerStatus
import com.rusticfox.fingenius.core.entities.PartnerType
import com.rusticfox.fingenius.core.exceptions.DatabaseException
import com.rusticfox.fingenius.core.exceptions.NotFoundException
import com.rusticfox.fingenius.core.ports.datastore.dto.PageRequest
import com.rusticfox.fingenius.core.ports.datastore.dto.PageSortOrder
import com.rusticfox.fingenius.core.values.Amount
import com.rusticfox.fingenius.datastore.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

class PartnerRepository(
    private val databaseClient: MongoCollection<PartnerModel>,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
): Repository<Partner, PartnerModel> {

    override suspend fun insert(data: Partner): PartnerModel = withContext(coroutineDispatcher) {
        val record = data.toPartnerModel()
        runCatching { databaseClient.insertOne(record) }
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
        return runCatching {
            val query = Filters.eq(PartnerModel::partnerId.name, id)
            databaseClient.find(query).firstOrNull()
        }
            .getOrElse { throw DatabaseException("insert failed $it", it) }
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

    /**
     * Retrieves all partners from the repository, filtered by the given criteria.
     *
     * @param type The partner type to filter by. If null, all types are returned.
     * @param status The partner status to filter by. If null, all statuses are returned.
     * @param openingBalance The opening balance to filter by. If null, all partners are returned.
     * @param pageRequest The page request containing the page number and size to retrieve.
     * @return A collection of partner models matching the given criteria, sorted by the natural order of the partner objects.
     */
    suspend fun findAll(type: PartnerType?, status: PartnerStatus?, openingBalance: Amount?, pageRequest: PageRequest): Collection<PartnerModel> = withContext(coroutineDispatcher) {
        runCatching {
            val filters = Filters.and(
                Filters.eq(Partner::type.name, type),
                Filters.eq(Partner::status.name, status),
                openingBalance?.let { Filters.gte(Amount::value.name, it.value) },
            )

            val sort = when(pageRequest.sortFields.order) {
                PageSortOrder.ASC -> {
                    Sorts.ascending(Partner::creationDate.name)
                }
                PageSortOrder.DESC -> {
                    Sorts.descending(Partner::creationDate.name)
                }
            }

            val partners = databaseClient.find(filters)
                .skip(pageRequest.number.toInt() * pageRequest.size)
                .limit(pageRequest.size)
                .sort(sort)
        }
            .getOrElse {
                throw DatabaseException("Failed to fetch partners", it)
            }
    }

    override suspend fun delete(id: String) {
        TODO("Not yet implemented")
    }
}
