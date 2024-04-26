package vn.travel.app.di

import org.koin.dsl.module

import vn.travel.domain.usecase.PagingNetworkUseCase
import vn.travel.domain.usecase.PagingNetworkUseCaseImpl

internal object DomainModules {
    val useCaseModules = module(createdAtStart = true) {
        factory<PagingNetworkUseCase> { PagingNetworkUseCaseImpl(get()) }
    }
}
