package com.rusticfox.fingenius.api.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonUnquotedLiteral
import kotlinx.serialization.json.jsonPrimitive
import java.math.BigInteger

object BigIntegerSerializer : KSerializer<BigInteger> {

  override val descriptor = PrimitiveSerialDescriptor("java.math.BigInteger", PrimitiveKind.LONG)

  /**
   * If decoding JSON uses [JsonDecoder.decodeJsonElement] to get the raw content,
   * otherwise decodes using [Decoder.decodeString].
   */
  override fun deserialize(decoder: Decoder): BigInteger =
    when (decoder) {
      is JsonDecoder -> decoder.decodeJsonElement().jsonPrimitive.content.toBigInteger()
      else           -> decoder.decodeString().toBigInteger()
    }

  /**
   * If encoding JSON uses [JsonUnquotedLiteral] to encode the exact [BigInteger] value.
   *
   * Otherwise, [value] is encoded using encodes using [Encoder.encodeString].
   */
  override fun serialize(encoder: Encoder, value: BigInteger) =
    when (encoder) {
      is JsonEncoder -> encoder.encodeJsonElement(JsonUnquotedLiteral(value.toString()))
      else           -> encoder.encodeString(value.toString())
    }
}