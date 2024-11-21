package com.rusticfox.fingenius.core.ports.datastore
/**
 * Generic write datastore interface that can be implemented by a datastore adapter or repository port to provide common
 * functionality for writing data to a repository
 * @param T the type of the entity
 */
interface WriteDatastore<T> {
    /**
     * Creates a new record in the repository
     */
    suspend fun create(data: T): T

    /**
     * Updates a given record in the repository
     */
    suspend fun update(data: T): T

    /**
     * Deletes a given record from the repository
     */
    suspend fun delete(id: String)
}
