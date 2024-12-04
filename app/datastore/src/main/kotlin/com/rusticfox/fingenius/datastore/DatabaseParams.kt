package com.rusticfox.fingenius.datastore

sealed interface DatabaseParams {
    val name: String
    val username: String
    val password: String
}

sealed interface MongoDBParams: DatabaseParams {
    override val name: String
    override val username: String
    override val password: String
}

data class MongoDatabaseParams(
    val url: String,
    override val name: String,
    override val username: String,
    override val password: String
): MongoDBParams

data class MongoDatabaseSettingsParams(
    override val name: String,
    override val username: String,
    override val password: String,
    val hosts: List<String> = emptyList()
): MongoDBParams
