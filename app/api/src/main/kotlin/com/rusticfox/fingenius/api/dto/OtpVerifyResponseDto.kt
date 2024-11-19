package com.rusticfox.fingenius.api.dto

import com.rusticfox.fingenius.core.entities.OtpVerificationStatus
import kotlinx.serialization.Serializable

@Serializable
data class OtpVerifyResponseDto(val userId: String, val code: String, val status: com.rusticfox.fingenius.core.entities.OtpVerificationStatus)
