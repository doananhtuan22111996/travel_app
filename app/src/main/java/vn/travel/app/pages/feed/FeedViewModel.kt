package vn.travel.app.pages.feed

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
import java.util.Locale

@OptIn(ExperimentalCoroutinesApi::class)
class FeedViewModel(private val useCase: AttractionUseCase) : BaseViewModel() {
	
	private val langCode = MutableSharedFlow<String>()
	
	val paging: Flow<PagingData<AttractionModel>>
	
	init {
		val lang = if (AppCompatDelegate.getApplicationLocales().toLanguageTags()
				.isEmpty()
		) Locale.getDefault().language else AppCompatDelegate.getApplicationLocales()
			.toLanguageTags()
		val langFlow = langCode.distinctUntilChanged().onStart {
			emit(
				lang.lowercase().replace("in", "id")
			)
		}
		paging = langFlow.flatMapLatest {
			load(it)
		}.cachedIn(viewModelScope)
	}
	
	fun onLanguage(lang: String) {
		viewModelScope.launch { langCode.emit(lang) }
	}
	
	private fun load(lang: String): Flow<PagingData<AttractionModel>> = useCase.execute(lang)
}