package com.rusticfox.fingenius.plugins

import io.ktor.server.application.Application

fun Application.plugins() {
    configureHTTP()
    configureHeadersPlugin()
    configureSerializationPlugin()
    configureLoggingPlugin(environment)
    configureMonitoringPlugin()
    configureSecurity(environment)
    configureRouting()
}
