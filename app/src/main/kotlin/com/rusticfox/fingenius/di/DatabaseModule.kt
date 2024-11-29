package com.rusticfox.fingenius.di

import com.rusticfox.fingenius.config.Config
import com.rusticfox.fingenius.datastore.DatabaseFactory
import com.rusticfox.fingenius.datastore.MongoDatabaseParams
import com.rusticfox.fingenius.datastore.partner.PartnerDataStoreAdapter
import com.rusticfox.fingenius.datastore.partner.PartnerReadDataStoreAdapter
import com.rusticfox.fingenius.datastore.partner.PartnerRepository
import com.rusticfox.fingenius.datastore.partner.PartnerWriteDataStoreAdapter
import com.rusticfox.fingenius.domain.port.outbound.datastore.PartnerDataStorePort
import com.rusticfox.fingenius.domain.port.outbound.datastore.PartnerReadDataStorePort
import com.rusticfox.fingenius.domain.port.outbound.datastore.PartnerWriteDataStorePort
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.dsl.module

interface DatabaseConfig {
    val host: String
    val port: Int
    val name: String
    val username: String
    val password: String
    val url: String
}

class DatabaseConfigImpl : DatabaseConfig, KoinComponent {

    private val config: Config by inject()
    override val host: String
        get() = config.getPropertyOrThrow("database.host")
    override val port: Int
        get() = config.getPropertyOrThrow("database.port").toInt()
    override val name: String
        get() = config.getPropertyOrThrow("database.name")

    override val username: String = config.getPropertyOrThrow("database.username")
    override val password: String = config.getPropertyOrThrow("database.password")

    override val url = config.getProperty(
        "database.url", "jdbc:${config.getPropertyOrThrow("database.driver")}://" +
                config.getPropertyOrThrow("database.host") + ":" +
                config.getPropertyOrThrow("database.port") + "/" +
                config.getPropertyOrThrow("database.name")
    )
}

val databaseModule = module {
    val databaseConfig = DatabaseConfigImpl()
    DatabaseFactory.mongoDatabase(
        MongoDatabaseParams(
            url = databaseConfig.url,
            username = databaseConfig.username,
            password = databaseConfig.password,
            name = databaseConfig.name
        )
    )
    single { PartnerRepository(get()) }
    single<PartnerWriteDataStorePort> { PartnerWriteDataStoreAdapter(get()) }
    single<PartnerReadDataStorePort> { PartnerReadDataStoreAdapter(get()) }
    single<PartnerDataStorePort> { PartnerDataStoreAdapter(get(), get(), get()) }
}
