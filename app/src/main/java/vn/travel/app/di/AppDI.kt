package vn.travel.app.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import vn.travel.app.pages.main.RootViewModel

internal object AppModules {
    val applicationModules = module(createdAtStart = true) {
    }

    val viewModelModules = module(createdAtStart = true) {
        viewModel { RootViewModel() }
    }
}
