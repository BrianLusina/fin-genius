package com.rusticfox.fingenius.di

import io.ktor.server.application.ApplicationEnvironment
import org.koin.dsl.module

fun appModule(environment: ApplicationEnvironment) = module {
    single { environment.log }
}
