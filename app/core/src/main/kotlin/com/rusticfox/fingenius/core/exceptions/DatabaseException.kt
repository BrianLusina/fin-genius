package com.rusticfox.fingenius.core.exceptions

/**
 * Generic DB exception
 */
class DatabaseException(message: String) : Exception(message) {
    constructor(message: String, cause: Throwable? = null) : this(message) {
        initCause(cause)
    }
}
