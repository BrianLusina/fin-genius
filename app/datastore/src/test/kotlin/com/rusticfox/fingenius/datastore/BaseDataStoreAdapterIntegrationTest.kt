package com.rusticfox.fingenius.datastore

import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.rusticfox.fingenius.testfixtures.extensions.MongoDatabaseExtension
import com.rusticfox.fingenius.testfixtures.utils.MONGO_DATABASE_NAME
import com.rusticfox.fingenius.testfixtures.utils.MONGO_DATABASE_PASSWORD
import com.rusticfox.fingenius.testfixtures.utils.MONGO_DATABASE_URL
import com.rusticfox.fingenius.testfixtures.utils.MONGO_DATABASE_USERNAME
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.koin.test.KoinTest

@ExtendWith(MongoDatabaseExtension::class)
abstract class BaseDataStoreAdapterIntegrationTest : KoinTest {
    lateinit var mongoDatabase: MongoDatabase

    @BeforeEach
    fun setup() {
        mongoDatabase = DatabaseFactory.mongoDatabase(
            MongoDatabaseSettingsParams(
                name = MONGO_DATABASE_NAME,
                username = MONGO_DATABASE_USERNAME,
                password = MONGO_DATABASE_PASSWORD,
                hosts = listOf()
            )
        )
    }
}