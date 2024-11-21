package com.rusticfox.fingenius.core.interactor

interface UseCase<in T, out R> {
    suspend operator fun invoke(request: T): R
}
