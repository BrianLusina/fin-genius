object Dependencies {
    object Ktor {
        private const val version = "3.0.1"
        const val coreJvm = "io.ktor:ktor-server-core-jvm:$version"
        const val openApi = "io.ktor:ktor-server-openapi:$version"
        const val serverContentNegotiation = "io.ktor:ktor-server-content-negotiation:$version"
        const val clientContentNegotiation = "io.ktor:ktor-client-content-negotiation:$version"
        const val metrics = "io.ktor:ktor-server-metrics-jvm:$version"
        const val metricsMicrometer = "io.ktor:ktor-server-metrics-micrometer-jvm:$version"
        const val callLogging = "io.ktor:ktor-server-call-logging-jvm:$version"
        const val callId = "io.ktor:ktor-server-call-id-jvm:$version"
        const val swagger = "io.ktor:ktor-server-swagger:$version"
        const val sessions = "io.ktor:ktor-server-sessions-jvm:$version"
        const val auth = "io.ktor:ktor-server-auth-jvm:$version"
        const val authJwt = "io.ktor:ktor-server-auth-jwt-jvm:$version"
        const val netty = "io.ktor:ktor-server-netty-jvm:$version"
        const val defaultHeaders = "io.ktor:ktor-server-default-headers:$version"

        const val serializationJson = "io.ktor:ktor-serialization-kotlinx-json-jvm:$version"

        const val serverTestHost = "io.ktor:ktor-server-test-host:$version"
    }

    object DI {
        private const val koinVersion = "4.0.0"
        const val koinCore = "io.insert-koin:koin-core:$koinVersion"
        const val koinCoreCoroutines = "io.insert-koin:koin-core-coroutines:$koinVersion"
        const val koinLogger = "io.insert-koin:koin-logger-slf4j:$koinVersion"
        const val koinKtor = "io.insert-koin:koin-ktor:$koinVersion"
        const val koinTest = "io.insert-koin:koin-test:$koinVersion"
        const val koinTestJunit5 = "io.insert-koin:koin-test-junit5:$koinVersion"
    }

    object Database {
        object MongoDb {
            const val bsonKotlinX = "org.mongodb:bson-kotlinx:5.2.0"
            const val driverCoroutine = "org.mongodb:mongodb-driver-kotlin-coroutine:5.2.0"
        }
    }

    object Utils {
        private const val logbackVersion = "1.2.11"
        const val logbackClassic = "ch.qos.logback:logback-classic:$logbackVersion"

        const val dotenv = "io.github.cdimascio:dotenv-kotlin:6.4.1"
        const val ksuid = "com.github.ksuid:ksuid:1.1.3"

        const val phoneLib = "com.googlecode.libphonenumber:libphonenumber:8.10.2"

        // detekt
        const val detektFormatting = "io.gitlab.arturbosch.detekt:detekt-formatting:${Plugins.Detekt.version}"
    }

    object Kotlin {
        object X {
            const val datetime = "org.jetbrains.kotlinx:kotlinx-datetime:0.6.1"
            private const val coroutinesVersion = "1.8.1-Beta"
            const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"
            const val serializationJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3"
            const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion"
        }

        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.KotlinVersion}"
    }

    object Test {
        const val mockK = "io.mockk:mockk:1.13.13"
        const val kotlinTest = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.KotlinVersion}"

        object Jupiter {
            private const val version = "5.8.2"
            const val test = "org.junit.jupiter:junit-jupiter:$version"
            const val engine = "org.junit.jupiter:junit-jupiter-engine:$version"
            const val api = "org.junit.jupiter:junit-jupiter-api:$version"
        }

        object Spek {
            private const val version = "2.0.19"
            const val dslJvm = "org.spekframework.spek2:spek-dsl-jvm:$version"
        }

        object TestContainers {
            private const val version = "1.20.3"
            const val junitJupiter = "org.testcontainers:junit-jupiter:$version"
            const val mongodb = "org.testcontainers:mongodb:$version"
        }
    }

    object Telemetry {
        private const val prometheusVersion = "1.14.1"
        const val micrometerRegistryPrometheus = "io.micrometer:micrometer-registry-prometheus:$prometheusVersion"
    }
}
