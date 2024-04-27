package vn.travel.app.pages.feed

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import vn.travel.app.base.BaseViewModel
import vn.travel.app.utils.Constants
import vn.travel.domain.model.AttractionModel
import vn.travel.domain.usecase.AttractionUseCase

class FeedViewModel(private val useCase: AttractionUseCase) : BaseViewModel() {

    private val langCode = MutableSharedFlow<String>()

    val paging: Flow<PagingData<AttractionModel>>

    init {
        val langFlow =
            langCode.distinctUntilChanged().onStart { emit(Constants.languages.last().first) }
        paging = langFlow.flatMapLatest {
            load(it)
        }.cachedIn(viewModelScope)
    }

    fun onLanguage(lang: String) {
        viewModelScope.launch { langCode.emit(lang) }
    }

    private fun load(lang: String): Flow<PagingData<AttractionModel>> = useCase.execute(lang)
}