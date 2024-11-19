plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":app:core"))
    implementation(Dependencies.Kotlin.X.datetime)
}
