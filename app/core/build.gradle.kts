plugins {
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(Dependencies.Kotlin.X.datetime)
    implementation(Dependencies.Utils.ksuid)
}
