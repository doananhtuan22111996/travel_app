package vn.travel.app.di

import org.koin.dsl.module

import vn.travel.domain.usecase.AttractionUseCase

internal object DomainModules {
    val useCaseModules = module(createdAtStart = true) {
        factory<AttractionUseCase> { AttractionUseCase(get()) }
    }
}
