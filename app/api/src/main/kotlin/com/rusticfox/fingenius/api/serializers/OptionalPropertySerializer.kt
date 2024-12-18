package com.rusticfox.fingenius.api.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class OptionalPropertySerializer<T>(private val valueSerializer: KSerializer<T>): KSerializer<OptionalProperty<T>> {
    override val descriptor: SerialDescriptor
        get() = valueSerializer.descriptor

    override fun deserialize(decoder: Decoder): OptionalProperty<T> {
        return OptionalProperty.Present(valueSerializer.deserialize(decoder))
    }

    override fun serialize(encoder: Encoder, value: OptionalProperty<T>) {
        when (value) {
            OptionalProperty.NotPresent -> {
                throw SerializationException("Tried to serialize an optional property that had no value present. " +
                        "Is encodeDefaults false?")
            }
            is OptionalProperty.Present -> {
                valueSerializer.serialize(encoder, value.value)
            }
        }
    }
}