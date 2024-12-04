package com.rusticfox.fingenius.plugins

import com.rusticfox.fingenius.api.partner.v1.partnerV1ApiRoutes
import io.ktor.server.application.Application
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.configureRouting() {
    routing {
        get("/health") {
            call.respondText("Healthy")
        }

        partnerV1ApiRoutes()
    }
}
