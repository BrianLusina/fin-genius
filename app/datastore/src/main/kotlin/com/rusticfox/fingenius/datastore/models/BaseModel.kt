package com.rusticfox.fingenius.datastore.models

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
@OptIn(ExperimentalSerializationApi::class)
open class BaseModel constructor(
    @JsonNames("_id")
    val id: String? = null,
    val creationDate: LocalDateTime = java.time.LocalDateTime.now().toKotlinLocalDateTime(),
    val updatedDate: LocalDateTime = creationDate,
    val deletedAt: LocalDateTime? = null,
)