package com.rusticfox.fingenius.core.services

import com.rusticfox.fingenius.core.entities.UserId
import com.rusticfox.fingenius.core.entities.OtpCode
import com.rusticfox.fingenius.core.interactor.UseCase

/**
 * Generates an OTP code for given user
 */
interface CreateOtpService :
    com.rusticfox.fingenius.core.interactor.UseCase<com.rusticfox.fingenius.core.entities.UserId, com.rusticfox.fingenius.core.entities.OtpCode> {
    override suspend fun execute(request: com.rusticfox.fingenius.core.entities.UserId): com.rusticfox.fingenius.core.entities.OtpCode
}
