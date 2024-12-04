package com.rusticfox.fingenius.api.partner.dto

import com.rusticfox.fingenius.api.dto.BigDecimalJson
import com.rusticfox.fingenius.api.serializers.OptionalProperty
import com.rusticfox.fingenius.api.serializers.OptionalPropertySerializer
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class PartnerDto(
    @Serializable(with = OptionalPropertySerializer::class)
    val type: OptionalProperty<String> = OptionalProperty.Present(""),

    @Serializable(with = OptionalPropertySerializer::class)
    val firstName: OptionalProperty<String>,

    @Serializable(with = OptionalPropertySerializer::class)
    val lastName: OptionalProperty<String>,

    @Serializable(with = OptionalPropertySerializer::class)
    val email: OptionalProperty<String>,

    @Serializable(with = OptionalPropertySerializer::class)
    val contactNo: OptionalProperty<String>,

    @Serializable(with = OptionalPropertySerializer::class)
    val address: OptionalProperty<String>,

    @Contextual
    val representative: CreatePartnerRepresentativeDto,

    @Contextual
    val openingBalance: BigDecimalJson
)
