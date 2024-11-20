plugins {
    kotlin("jvm")
    id(Plugins.KotlinSerialization.plugin) version Plugins.KotlinSerialization.version
    id(Plugins.Ktor.plugin) version Plugins.Ktor.version
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    implementation(project(":app:api"))
    implementation(project(":app:core"))
    implementation(project(":app:datastore"))
    implementation(project(":app:domain"))

    implementation(Dependencies.Kotlin.X.datetime)

    implementation(Dependencies.Ktor.coreJvm)
    implementation(Dependencies.Ktor.openApi)
    implementation(Dependencies.Ktor.serializationJson)
    implementation(Dependencies.Ktor.serverContentNegotiation)
    implementation(Dependencies.Ktor.metrics)
    implementation(Dependencies.Ktor.metricsMicrometer)
    implementation(Dependencies.Ktor.callLogging)
    implementation(Dependencies.Ktor.callId)
    implementation(Dependencies.Ktor.swagger)
    implementation(Dependencies.Ktor.sessions)
    implementation(Dependencies.Ktor.auth)
    implementation(Dependencies.Ktor.authJwt)
    implementation(Dependencies.Ktor.netty)
    implementation(Dependencies.Ktor.defaultHeaders)

    implementation(Dependencies.DI.koinCore)
    implementation(Dependencies.DI.koinKtor)
    implementation(Dependencies.DI.koinLogger)

    implementation(Dependencies.Utils.dotenv)

    implementation(Dependencies.Database.MongoDb.driverCoroutine)
    implementation(Dependencies.Database.MongoDb.bsonKotlinX)

    implementation(Dependencies.Telemetry.micrometerRegistryPrometheus)
    implementation(Dependencies.Utils.logbackClassic)

    testImplementation(testFixtures(project(":libs:testfixtures")))
    testImplementation(Dependencies.Ktor.serverTestHost)
    testImplementation(Dependencies.Ktor.serverContentNegotiation)
    testImplementation(Dependencies.Ktor.clientContentNegotiation)
    testImplementation(Dependencies.DI.koinTest)
    testImplementation(Dependencies.DI.koinTestJunit5)
}
