package com.rusticfox.fingenius.api.dto

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class ApiResult(
    val message: String? = null,
    val status: Int,
    @Contextual
    val data: Any? = null
)
