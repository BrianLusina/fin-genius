plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":app:core"))

    implementation(Dependencies.Database.MongoDb.bsonKotlinX)
    implementation(Dependencies.Database.MongoDb.driverCoroutine)

    implementation(Dependencies.Kotlin.X.datetime)

    testImplementation(testFixtures(project(":libs:testfixtures")))
    testImplementation(Dependencies.DI.koinCore)
    testImplementation(Dependencies.DI.koinTest)
    testImplementation(Dependencies.DI.koinTestJunit5)
}
