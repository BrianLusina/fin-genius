package com.rusticfox.fingenius.datastore.models

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.serialization.Serializable

@Serializable
open class BaseModel(
    val creationDate: LocalDateTime = java.time.LocalDateTime.now().toKotlinLocalDateTime(),
    val updatedDate: LocalDateTime = creationDate,
    val deletedAt: LocalDateTime? = null,
)