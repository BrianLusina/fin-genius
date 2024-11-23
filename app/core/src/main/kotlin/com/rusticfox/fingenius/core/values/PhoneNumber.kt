package com.rusticfox.fingenius.core.values

import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil


@JvmInline
value class PhoneNumber(val value: String) {
    init {
        require(value.isNotBlank()) {
            "Phone number $value provided can not be blank"
        }
        val phoneUtil = PhoneNumberUtil.getInstance()

        try {
            // TODO: Provide region code to parser
            phoneUtil.parse(value,  "KE")
        } catch (e: NumberParseException) {
            throw IllegalArgumentException("Phone number $value is invalid")
            System.err.println("NumberParseException was thrown: $e")
        }
    }
}
