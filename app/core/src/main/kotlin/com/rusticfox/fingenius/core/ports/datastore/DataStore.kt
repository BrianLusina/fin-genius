package com.rusticfox.fingenius.core.ports.datastore

/**
 * Generic datastore interface that can be implemented by a datastore adapter or repository port to provide common
 * functionality for a repository
 * @param T the type of the entity
 */
interface DataStore<T> : ReadDataStore<T>, WriteDatastore<T> {
    /**
     * Checks for the existence of a record at the repository
     */
    suspend fun exists(id: String): Boolean
}
