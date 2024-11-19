package com.rusticfox.fingenius.di

import com.rusticfox.fingenius.api.OtpService
import org.koin.dsl.module

val apiModule = module {
    single { OtpService(get(), get()) }
}
