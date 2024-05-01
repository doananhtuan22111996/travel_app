package vn.travel.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import vn.travel.domain.model.AttractionModel
import vn.travel.domain.repository.AttractionRepository

class AttractionUseCase(private val repository: AttractionRepository) :
    BaseUseCase<String, PagingData<AttractionModel>>() {
    override fun execute(vararg params: String?): Flow<PagingData<AttractionModel>> =
        repository.getAttractions(params.first() ?: "en")
}
