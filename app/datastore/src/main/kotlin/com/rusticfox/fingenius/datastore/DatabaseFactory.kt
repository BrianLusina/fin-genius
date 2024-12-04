package com.rusticfox.fingenius.datastore

import com.mongodb.MongoClientSettings
import com.mongodb.ServerAddress
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase

object DatabaseFactory {

    fun mongoDatabase(config: MongoDatabaseParams): MongoDatabase {
        val mongoClient = MongoClient.create(config.url)
        val database = mongoClient.getDatabase(config.name)
        return database
    }

    fun mongoDatabase(config: MongoDatabaseSettingsParams): MongoDatabase {
        val serverAddresses = config.hosts.map {
            val (host, port) = it.split(":")
            ServerAddress(host, port.toInt())
        }

        val settings = MongoClientSettings
            .builder()
            .applyToClusterSettings { it.hosts(serverAddresses) }
            .build()

        val mongoClient = MongoClient.create(settings)
        val database = mongoClient.getDatabase(config.name)
        return database
    }
}
