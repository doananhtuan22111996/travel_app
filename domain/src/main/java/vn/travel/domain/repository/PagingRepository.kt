package vn.travel.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import vn.travel.domain.model.AttractionModel

interface PagingRepository {

    suspend fun getPagingNetwork(): Flow<PagingData<AttractionModel>>
}
