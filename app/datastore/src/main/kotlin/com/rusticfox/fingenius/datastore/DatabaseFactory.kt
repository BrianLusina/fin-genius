package com.rusticfox.fingenius.datastore

import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.rusticfox.fingenius.datastore.models.OtpTable
import kotlinx.coroutines.Dispatchers
import javax.sql.DataSource

object DatabaseFactory {
    fun init(config: DatabaseParams): MongoDatabase {
        val mongoClient = MongoClient.create(config.url)
        val database = mongoClient.getDatabase(config.databaseName)
        return database
    }

    private fun createDataSource(
        dbUrl: String,
        user: String,
        dbPassword: String,
        driverName: String
    ): DataSource {
        return HikariDataSource(HikariConfig()
            .apply {
                jdbcUrl = dbUrl
                username = user
                password = dbPassword
                driverClassName = driverName
                isAutoCommit = true
                transactionIsolation = "TRANSACTION_REPEATABLE_READ"
                addDataSourceProperty("cachePrepStmts", "true")
                addDataSourceProperty("prepStmtCacheSize", "250")
                addDataSourceProperty("prepStmtCacheSqlLimit", "2048")
                validate()
            })
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}
