package com.rusticfox.fingenius.datastore

import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase

object DatabaseFactory {
    fun init(config: DatabaseParams): MongoDatabase {
        val mongoClient = MongoClient.create(config.url)
        val database = mongoClient.getDatabase(config.databaseName)
        return database
    }
}
