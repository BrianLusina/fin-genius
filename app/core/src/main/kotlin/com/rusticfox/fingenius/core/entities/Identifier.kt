package com.rusticfox.fingenius.core.entities

open class Identifier<T>(val value: T) {
    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }

        if (other !is Identifier<*>) {
            return false
        }

        return other.value == this.value
    }

    override fun toString(): String {
        return value.toString()
    }

    override fun hashCode(): Int {
        return value?.hashCode() ?: 0
    }
}
