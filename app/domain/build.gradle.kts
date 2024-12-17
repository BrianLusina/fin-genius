plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":app:core"))
    implementation(Dependencies.Kotlin.X.datetime)
    implementation(Dependencies.Kotlin.X.coroutinesCore)
    testImplementation(Dependencies.Kotlin.X.coroutinesTest)
}
