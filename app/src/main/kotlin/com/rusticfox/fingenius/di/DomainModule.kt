package com.rusticfox.fingenius.di

import com.rusticfox.fingenius.core.services.CreateOtpService
import com.rusticfox.fingenius.core.services.OtpCodeGenerator
import com.rusticfox.fingenius.core.services.VerifyOtpService
import com.sanctumlabs.otp.di.generators.GoogleOtpGeneratorConfigImpl
import com.sanctumlabs.otp.di.generators.HmacOtpGeneratorConfigImpl
import com.sanctumlabs.otp.di.generators.TimeOtpGeneratorConfigImpl
import com.sanctumlabs.otp.domain.generators.GoogleCodeGenerator
import com.sanctumlabs.otp.domain.generators.HmacAlgorithms
import com.rusticfox.fingenius.domain.generators.HmacBasedCodeGenerator
import com.rusticfox.fingenius.domain.generators.HmacCodeGeneratorConfig
import com.sanctumlabs.otp.domain.generators.TimeBasedCodeGeneratorConfig
import com.sanctumlabs.otp.domain.generators.TimeBasedOtpCodeGenerator
import com.sanctumlabs.otp.domain.services.CreateOtpServiceImpl
import com.sanctumlabs.otp.domain.services.VerifyOtpServiceImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

val domainModule = module {
    val timeOtpCodeGenQualifierName = "totp"
    val hmacOtpCodeGenQualifierName = "hotp"
    val googleOtpCodeGenQualifierName = "gotp"

    val timeOtpGenConfig = TimeOtpGeneratorConfigImpl()
    val timeBasedCodeGenConfig = TimeBasedCodeGeneratorConfig(
        codeDigits = timeOtpGenConfig.codeDigits,
        hmacAlgorithm = HmacAlgorithms.valueOf(timeOtpGenConfig.hmacAlgorithm),
        timeStep = timeOtpGenConfig.timeStep,
        timeStepUnit = TimeUnit.valueOf(timeOtpGenConfig.timeStepUnit)
    )

    val hmacOtpGenConfig = HmacOtpGeneratorConfigImpl()
    val hmacCodeConfig = com.rusticfox.fingenius.domain.generators.HmacCodeGeneratorConfig(
        codeDigits = hmacOtpGenConfig.codeDigits,
        hmacAlgorithm = HmacAlgorithms.valueOf(hmacOtpGenConfig.hmacAlgorithm)
    )

    val googleOtpGenConfig = GoogleOtpGeneratorConfigImpl()

    single<com.rusticfox.fingenius.core.services.OtpCodeGenerator>(named(timeOtpCodeGenQualifierName)) {
        TimeBasedOtpCodeGenerator(
            timeOtpGenConfig.key,
            timeBasedCodeGenConfig
        )
    }
    single<com.rusticfox.fingenius.core.services.OtpCodeGenerator>(named(hmacOtpCodeGenQualifierName)) {
        com.rusticfox.fingenius.domain.generators.HmacBasedCodeGenerator(
            hmacOtpGenConfig.key,
            hmacCodeConfig
        )
    }
    single<com.rusticfox.fingenius.core.services.OtpCodeGenerator>(named(googleOtpCodeGenQualifierName)) { GoogleCodeGenerator(googleOtpGenConfig.key) }

    var otpCodeGeneratorToUse = timeOtpCodeGenQualifierName
    if (timeOtpGenConfig.enabled) {
        otpCodeGeneratorToUse = timeOtpCodeGenQualifierName
    } else if (hmacOtpGenConfig.enabled) {
        otpCodeGeneratorToUse = hmacOtpCodeGenQualifierName
    } else if (googleOtpGenConfig.enabled) {
        otpCodeGeneratorToUse = googleOtpCodeGenQualifierName
    }

    single<com.rusticfox.fingenius.core.services.CreateOtpService> { CreateOtpServiceImpl(get(), get(qualifier = named(otpCodeGeneratorToUse))) }
    single<com.rusticfox.fingenius.core.services.VerifyOtpService> { VerifyOtpServiceImpl(get()) }
}
