package vn.travel.app.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import vn.travel.app.pages.detail.DetailViewModel
import vn.travel.app.pages.feed.FeedViewModel
import vn.travel.app.pages.main.RootViewModel
import vn.travel.app.pages.map.MapViewModel
import vn.travel.domain.usecase.AttractionUseCase

internal object AppModules {
	val viewModelModules = module(createdAtStart = true) {
		viewModel { RootViewModel() }
		viewModel { FeedViewModel(get<AttractionUseCase>()) }
		viewModel { DetailViewModel() }
		viewModel { MapViewModel() }
	}
}
