package com.rusticfox.fingenius.domain.usecases.partner.create

class CreatePartnerException(message: String) : Exception(message) {
    constructor(message: String, cause: Throwable? = null) : this(message) {
        initCause(cause)
    }
}
