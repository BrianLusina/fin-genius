package com.rusticfox.fingenius.core.ports.datastore

import com.rusticfox.fingenius.core.ports.datastore.dto.PageRequest

/**
 * Generic read datastore interface that can be implemented by a datastore adapter or datastore port to provide common
 * functionality for reading data from a datastore
 * @param T the type of the entity
 */
interface ReadDataStore<T> {

    /**
     * Finds a given record by the given ID
     */
    suspend fun findById(id: String): T?

    /**
     * Retrieves all records of a given type from the repository
     */
    suspend fun findAll(pageRequest: PageRequest): Collection<T>
}

