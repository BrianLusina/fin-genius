plugins {
    kotlin("jvm")
    id(Plugins.KotlinSerialization.plugin)
}

dependencies {
    implementation(project(":app:core"))

    implementation(Dependencies.Ktor.coreJvm)
    implementation(Dependencies.Ktor.auth)
    implementation(Dependencies.Ktor.authJwt)
    implementation(Dependencies.Ktor.netty)
    implementation(Dependencies.Ktor.serializationJson)
    implementation(Dependencies.Ktor.netty)
    implementation(Dependencies.Ktor.defaultHeaders)
    implementation(Dependencies.Ktor.openApi)

    implementation(Dependencies.Kotlin.X.datetime)

    implementation(Dependencies.DI.koinKtor)

    testImplementation(Dependencies.Ktor.serverContentNegotiation)
    testImplementation(Dependencies.Ktor.clientContentNegotiation)
    testImplementation(Dependencies.Ktor.serverTestHost)
    testImplementation(Dependencies.DI.koinTest)
    testImplementation(Dependencies.DI.koinTestJunit5)
}
