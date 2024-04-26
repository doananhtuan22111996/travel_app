package vn.travel.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import timber.log.Timber
import vn.travel.data.network.PagingByNetworkDataSource
import vn.travel.data.model.ListResponse
import vn.travel.data.service.ApiService
import vn.travel.domain.repository.PagingRepository

class PagingRepositoryImpl(private val apiService: ApiService) {
//
//    override suspend fun getPagingNetwork(): Flow<PagingData<ItemModel>> = Pager(
//        config = PagingConfig(15),
//    ) {
//        object : PagingByNetworkDataSource<ItemRaw, ItemModel>() {
//            override suspend fun onApi(page: Int?): Response<ListResponse<ItemRaw>> =
//                apiService.getPaging(page = page ?: 1)
//
//            override suspend fun processResponse(request: ListResponse<ItemRaw>?): ListResponse<ItemModel> {
//                // Save Data to Room
//                Timber.d("processResponse: ${itemDao.getItems()}")
//                return ListResponse(data = request?.data?.map {
//                    it.raw2Model() as ItemModel
//                }, metadata = request?.metadata)
//            }
//        }
//    }.flow
}
