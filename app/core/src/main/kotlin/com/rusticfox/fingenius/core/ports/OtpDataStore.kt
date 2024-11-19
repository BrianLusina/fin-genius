package com.rusticfox.fingenius.core.ports

import com.rusticfox.fingenius.core.entities.UserId
import com.rusticfox.fingenius.core.entities.OtpCode

interface OtpDataStore {
    /**
     * Save generated OTP or updates an existing OTP
     */
    suspend fun create(otpCode: com.rusticfox.fingenius.core.entities.OtpCode): com.rusticfox.fingenius.core.entities.OtpCode

    /**
     * Find OTP code by the code by otp code & channel
     * @param code [OtpCode] passed in OTP code
     * @return [OtpCode] OTP entity
     */
    suspend fun getOtpCode(code: String): com.rusticfox.fingenius.core.entities.OtpCode

    /**
     * Finds all OtpCode(s)
     */

    suspend fun getAllByUserId(userId: com.rusticfox.fingenius.core.entities.UserId): Collection<com.rusticfox.fingenius.core.entities.OtpCode>

    suspend fun getAll(): Collection<com.rusticfox.fingenius.core.entities.OtpCode>

    /**
     * Marks OTP code as used
     */
    suspend fun markOtpAsUsed(otpCode: com.rusticfox.fingenius.core.entities.OtpCode)
}
