package vn.travel.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import vn.travel.domain.model.AttractionModel
import vn.travel.domain.repository.PagingRepository

interface PagingNetworkUseCase {

    suspend fun getPagingNetwork(): Flow<PagingData<AttractionModel>>
}

class PagingNetworkUseCaseImpl(private val repository: PagingRepository) : PagingNetworkUseCase {

    override suspend fun getPagingNetwork(): Flow<PagingData<AttractionModel>> {
        return repository.getPagingNetwork()
    }
}
