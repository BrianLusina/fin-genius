package com.rusticfox.fingenius.api.serializers

sealed class OptionalProperty<out T> {
    data object NotPresent: OptionalProperty<Nothing>()

    data class Present<T>(val value: T): OptionalProperty<T>()
}
