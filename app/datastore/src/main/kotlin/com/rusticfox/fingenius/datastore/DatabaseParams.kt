package com.rusticfox.fingenius.datastore

data class DatabaseParams(
    val url: String,
    val databaseName: String,
    val username: String,
    val password: String,
    val cleanDB: Boolean = false,
)
