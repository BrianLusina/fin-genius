package com.rusticfox.fingenius.domain.generators

import com.rusticfox.fingenius.core.services.GeneratedOtpCode
import com.rusticfox.fingenius.core.services.OtpCodeGenerator
import dev.turingcomplete.kotlinonetimepassword.HmacAlgorithm
import dev.turingcomplete.kotlinonetimepassword.HmacOneTimePasswordConfig
import dev.turingcomplete.kotlinonetimepassword.HmacOneTimePasswordGenerator
import kotlinx.datetime.toKotlinLocalDateTime
import java.time.LocalDateTime
import java.util.*

// number of seconds the current OTP is still valid
private const val AMOUNT_TO_ADD_IN_SECONDS = 30

data class HmacCodeGeneratorConfig(
    val codeDigits: Int,
    val hmacAlgorithm: com.rusticfox.fingenius.domain.generators.HmacAlgorithms = com.rusticfox.fingenius.domain.generators.HmacAlgorithms.SHA1
)

class HmacBasedCodeGenerator(
    private val key: String,
    private val config: com.rusticfox.fingenius.domain.generators.HmacCodeGeneratorConfig
) : com.rusticfox.fingenius.core.services.OtpCodeGenerator {

    override fun generate(value: String): com.rusticfox.fingenius.core.services.GeneratedOtpCode {
        val hmacConfig = HmacOneTimePasswordConfig(
            codeDigits = config.codeDigits,
            hmacAlgorithm = when (config.hmacAlgorithm) {
                com.rusticfox.fingenius.domain.generators.HmacAlgorithms.SHA256 -> HmacAlgorithm.SHA256
                com.rusticfox.fingenius.domain.generators.HmacAlgorithms.SHA1 -> HmacAlgorithm.SHA1
                com.rusticfox.fingenius.domain.generators.HmacAlgorithms.SHA512 -> HmacAlgorithm.SHA512
            },
        )

        val secret = "$key{$value}"
        val encodedSecret = secret.toByteArray()

        val generator = HmacOneTimePasswordGenerator(secret = encodedSecret, config = hmacConfig)
        val otpCode = generator.generate(counter = 0)

        val currentTime = Date(System.currentTimeMillis())

        val calendar = Calendar.getInstance()
        calendar.time = currentTime
        calendar.add(Calendar.SECOND, com.rusticfox.fingenius.domain.generators.AMOUNT_TO_ADD_IN_SECONDS)

        val expiryTime = LocalDateTime.of(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
            calendar.get(Calendar.HOUR),
            calendar.get(Calendar.MINUTE)
        ).toKotlinLocalDateTime()

        return com.rusticfox.fingenius.core.services.GeneratedOtpCode(
            code = otpCode,
            expiryTime = expiryTime
        )
    }
}
