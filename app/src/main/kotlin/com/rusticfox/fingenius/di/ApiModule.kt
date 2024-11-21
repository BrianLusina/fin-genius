package com.rusticfox.fingenius.di

import com.rusticfox.fingenius.api.partner.PartnerService
import org.koin.dsl.module

val apiModule = module {
    single { PartnerService(get(), get()) }
}
