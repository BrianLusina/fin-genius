package com.rusticfox.fingenius.domain.services

class CreateOtpException(message: String) : Exception(message) {
    constructor(message: String, cause: Throwable? = null) : this(message) {
        initCause(cause)
    }
}
