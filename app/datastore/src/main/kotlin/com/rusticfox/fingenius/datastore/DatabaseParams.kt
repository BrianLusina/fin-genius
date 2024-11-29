package com.rusticfox.fingenius.datastore

sealed interface DatabaseParams {
    val name: String
    val username: String
    val password: String
}

sealed interface MongoDatabaseParams: DatabaseParams {
    override val name: String
    override val username: String
    override val password: String
}

data class MongoDatabaseSettingsParams(
    override val name: String,
    override val username: String,
    override val password: String,
    val hosts: List<String> = emptyList()
): MongoDatabaseParams
