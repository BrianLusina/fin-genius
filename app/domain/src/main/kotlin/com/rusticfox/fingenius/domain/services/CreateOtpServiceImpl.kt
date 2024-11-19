package com.rusticfox.fingenius.domain.services

import com.rusticfox.fingenius.core.entities.UserId
import com.rusticfox.fingenius.core.entities.OtpCode
import com.rusticfox.fingenius.core.ports.OtpDataStore
import com.rusticfox.fingenius.core.services.CreateOtpService
import com.rusticfox.fingenius.core.services.OtpCodeGenerator

/**
 * Creates an OTP code for given user
 */
class CreateOtpServiceImpl(
    private val dataStore: com.rusticfox.fingenius.core.ports.OtpDataStore,
    private val otpGenerator: com.rusticfox.fingenius.core.services.OtpCodeGenerator,
) : com.rusticfox.fingenius.core.services.CreateOtpService {

    override suspend fun execute(request: com.rusticfox.fingenius.core.entities.UserId): com.rusticfox.fingenius.core.entities.OtpCode {
        return runCatching {
            val generatedCode = otpGenerator.generate(request.value)
            val (code, expiryTime) = generatedCode

            com.rusticfox.fingenius.core.entities.OtpCode(code = code, userId = request, expiryTime = expiryTime)
        }
            .onSuccess { dataStore.create(it) }
            .getOrElse {
                throw CreateOtpException("Failed to create OTP for $request", it)
            }
    }
}
