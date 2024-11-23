package com.rusticfox.fingenius.core.values

@JvmInline
value class Email(val value: String) {
    init {
        require(value.isNotBlank()) {
            "Email $value provided can not be blank"
        }

        val emailRegex = Regex("^[\\w\\-\\.]+@([\\w-]+\\.)+[\\w-]{2,}\$")
        require(emailRegex.matches(value)) {
            "Email $value provided is invalid"
        }
    }
}
