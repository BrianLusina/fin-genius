package com.rusticfox.fingenius.core.values

@JvmInline
value class Name(val value: String) {
    init {
        require(value.isNotBlank()) {
            "Name $value provided can not be blank"
        }
    }
}
