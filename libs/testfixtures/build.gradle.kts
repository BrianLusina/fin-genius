plugins {
    java
    `java-test-fixtures`
    kotlin("jvm")
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    testFixturesImplementation(project(":app:core"))

    testFixturesImplementation(Dependencies.Kotlin.X.datetime)
    testFixturesImplementation(Dependencies.Utils.ksuid)
    testFixturesImplementation(Dependencies.Utils.phoneLib)

    testFixturesImplementation(Dependencies.Test.TestContainers.mongodb)
    testFixturesImplementation(Dependencies.Test.TestContainers.junitJupiter)
    testFixturesImplementation(Dependencies.Test.Jupiter.api)
    testFixturesImplementation(Dependencies.Test.Jupiter.engine)
    testFixturesImplementation(Dependencies.Test.Jupiter.test)
}
