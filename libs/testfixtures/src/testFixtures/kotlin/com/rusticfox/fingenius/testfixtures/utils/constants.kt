package com.rusticfox.fingenius.testfixtures.utils


const val DATABASE_DRIVER = "postgresql"
const val DATABASE_DRIVER_CLASS = "org.testcontainers.jdbc.ContainerDatabaseDriver"

const val MONGO_DATABASE_VERSION = "mongo:8.0.3"
const val MONGO_DATABASE_PORT = 27017
const val MONGO_DATABASE_USERNAME = "fingenius-user"
const val MONGO_DATABASE_PASSWORD = "fingenius-password"
const val MONGO_DATABASE_NAME = "fingeniusdb"
const val MONGO_DATABASE_URL = "mongodb://localhost:$MONGO_DATABASE_PORT"

const val TEST_TYPE_UNIT = "unit"
const val TEST_TYPE_INTEGRATION = "integration"
