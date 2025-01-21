package com.rusticfox.fingenius.domain.usecases.partner.edit

class UpdatePartnerException(message: String) : Exception(message) {
    constructor(message: String, cause: Throwable? = null) : this(message) {
        initCause(cause)
    }
}
