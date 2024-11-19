package com.rusticfox.fingenius.core.interactor

interface UseCase<in T, out R> {
    suspend fun execute(request: T): R
}
