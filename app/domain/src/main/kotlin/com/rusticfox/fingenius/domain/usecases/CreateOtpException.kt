package com.rusticfox.fingenius.domain.usecases

class CreateOtpException(message: String) : Exception(message) {
    constructor(message: String, cause: Throwable? = null) : this(message) {
        initCause(cause)
    }
}
