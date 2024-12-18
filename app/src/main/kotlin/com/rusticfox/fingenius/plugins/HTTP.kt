package com.rusticfox.fingenius.plugins

import io.ktor.server.application.Application
import io.ktor.server.routing.*
import io.ktor.server.plugins.openapi.*
import io.ktor.server.plugins.swagger.*

// Ref: https://ktor.io/docs/openapi.html#configure-swagger
fun Application.configureHTTP() {
    routing {
        openAPI(path = "openapi")
    }
    routing {
        swaggerUI(path = "openapi")
    }
}
