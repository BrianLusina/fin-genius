package com.rusticfox.fingenius.testfixtures.utils

import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.utility.DockerImageName

/**
 * TestDatabase is a wrapper around a test database container that is used to run integration tests.
 */
object MongoDatabaseTestContainer {
    private lateinit var mongoDBContainer: MongoDBContainer

    /**
     * Initializes a database container for testing.
     * @param username the username to use for the database
     * @param password the password to use for the database
     * @param database the name of the database to use
     * @param ports the ports to use for the database
     * @param reuse whether to re-use this database container
     * @param version the version of PostgreSQL to use
     */
    @Suppress("LongParameterList", "SpreadOperator")
    fun init(
        username: String = MONGO_DATABASE_USERNAME,
        password: String = MONGO_DATABASE_PASSWORD,
        database: String = MONGO_DATABASE_NAME,
        vararg ports: Int = intArrayOf(MONGO_DATABASE_PORT),
        reuse: Boolean = true,
        version: String = MONGO_DATABASE_VERSION
    ): MongoDBContainer {
        mongoDBContainer = MongoDBContainer(DockerImageName.parse(version))
            .withExposedPorts(*ports.toTypedArray())
            .withReuse(reuse)
            .withEnv("MONGO_INITDB_DATABASE", database)
            .withEnv("MONGO_INITDB_ROOT_USERNAME", username)
            .withEnv("MONGO_INITDB_ROOT_PASSWORD", password)

        return mongoDBContainer
    }
}
