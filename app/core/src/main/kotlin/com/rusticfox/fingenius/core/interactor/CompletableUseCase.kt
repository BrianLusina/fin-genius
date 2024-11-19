package com.rusticfox.fingenius.core.interactor

interface CompletableUseCase<in T> : com.rusticfox.fingenius.core.interactor.UseCase<T, Unit> {
    override suspend fun execute(request: T)
}
