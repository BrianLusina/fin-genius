package com.rusticfox.fingenius.api.dto

import com.rusticfox.fingenius.api.serializers.BigDecimalSerializer
import com.rusticfox.fingenius.api.serializers.BigIntegerSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.math.BigInteger

typealias BigDecimalJson = @Serializable(with = BigDecimalSerializer::class) BigDecimal
typealias BigIntegerJson = @Serializable(with = BigIntegerSerializer::class) BigInteger
