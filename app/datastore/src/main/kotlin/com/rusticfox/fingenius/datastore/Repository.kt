package com.rusticfox.fingenius.datastore

import com.rusticfox.fingenius.core.ports.datastore.dto.PageRequest

/**
 * Generic repository that can be inherited by a repository to handle interactions with a database
 * @param T domain entity
 * @param R database model
 */
interface Repository<T, R> {
    /**
     * Creates a new record in the repository
     */
    suspend fun insert(data: T): R

    /**
     * Finds a given record by the given ID
     */
    suspend fun findById(id: String): R?

    /**
     * Retrieves all records of a given type from the repository
     */
    suspend fun findAll(pageRequest: PageRequest): Collection<R>

    /**
     * Updates a given record in the repository
     */
    suspend fun update(data: T)

    /**
     * Deletes a given record from the repository
     */
    suspend fun delete(id: String)
}
