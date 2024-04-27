package vn.travel.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import timber.log.Timber
import vn.travel.data.model.AttractionRaw
import vn.travel.data.model.ListResponse
import vn.travel.data.network.PagingByNetworkDataSource
import vn.travel.data.service.ApiService
import vn.travel.domain.model.AttractionModel
import vn.travel.domain.repository.AttractionRepository

class AttractionRepositoryImpl(private val apiService: ApiService) : AttractionRepository {
    override fun getAttractions(
        lang: String
    ): Flow<PagingData<AttractionModel>> = Pager(
        config = PagingConfig(25),
    ) {
        object : PagingByNetworkDataSource<AttractionRaw, AttractionModel>() {
            override suspend fun onApi(page: Int?): Response<ListResponse<AttractionRaw>> =
                apiService.attractions(lang, page = page ?: 1)

            override suspend fun processResponse(request: ListResponse<AttractionRaw>?): ListResponse<AttractionModel> {
                return ListResponse(data = request?.data?.map {
                    it.raw2Model()
                }, total = request?.total ?: 0)
            }
        }
    }.flow
}