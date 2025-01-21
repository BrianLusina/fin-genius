package com.rusticfox.fingenius.domain.usecases.partner

class CreatePartnerException(message: String) : Exception(message) {
    constructor(message: String, cause: Throwable? = null) : this(message) {
        initCause(cause)
    }
}

class UpdatePartnerException(message: String) : Exception(message) {
    constructor(message: String, cause: Throwable? = null) : this(message) {
        initCause(cause)
    }
}

class GetPartnerByIdException(message: String): Exception(message) {
    constructor(message: String, cause: Throwable? = null) : this(message) {
        initCause(cause)
    }
}

