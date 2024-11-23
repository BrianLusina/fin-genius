package com.rusticfox.fingenius.api

import com.rusticfox.fingenius.api.partner.v1.partnerV1ApiRoutes
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.routing.routing
import io.ktor.client.HttpClient
import io.ktor.server.application.Application
import io.ktor.server.testing.testApplication
import kotlinx.serialization.json.Json

fun Application.configureRouting() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }

    routing {
        partnerV1ApiRoutes()
    }
}

/**
 * Provides a wrapper around [testApplication] with a pre-configured [HttpClient] with necessary plugins already installed
 * for us in testing
 * Usage:
 *
 * ```kotlin
 * @Test
 * fun `should return success in response`() = customTestApplication { testHttpClient ->
 *  // your test
 * }
 * ```
 */
fun customTestApplication(testBlock: suspend (httpClient: HttpClient) -> Unit) = testApplication {
    application {
        configureRouting()
    }

    val testHttpClient = createClient {
        install(plugin = io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
            json()
        }
    }

    testBlock(testHttpClient)
}
