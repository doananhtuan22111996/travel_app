package vn.travel.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import retrofit2.Response
import timber.log.Timber
import vn.travel.data.model.ListResponse

/**
 * A [PagingSource] that uses the before/after keys returned in page requests.
 *
 * @see PagingByNetworkDataSource
 */
abstract class PagingByNetworkDataSource<RequestType : Any, ResultType : Any>(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) :
    PagingSource<Int, ResultType>() {

    override fun getRefreshKey(state: PagingState<Int, ResultType>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            // This loads starting from previous page, but since PagingConfig.initialLoadSize spans
            // multiple pages, the initial load will still load items centered around
            // anchorPosition. This also prevents needing to immediately launch prepend due to
            // prefetchDistance.
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultType> {
        return withContext(dispatcher) {
            val apiResponse = onApi(page = params.key)
            if (apiResponse.isSuccessful) {
                val body = apiResponse.body()
                val response = processResponse(body)
                val nextKey =
                    if (params.key == null) 1 else if ((response?.total ?: 0) > 0) params.key?.plus(
                        1
                    ) else null
                Timber.d("PagingByNetworkDataSource Success: + ${response?.total} + nextKey: $nextKey")
                LoadResult.Page(
                    data = response?.data ?: listOf(), prevKey = null, nextKey = nextKey
                )
            } else {
                try {
                    val result = Gson().fromJson(
                        apiResponse.errorBody()?.string(), String::class.java
                    )
                    Timber.e("PagingByNetworkDataSource Failure: $result")
                    LoadResult.Error(Throwable(result))
                } catch (e: Exception) {
                    LoadResult.Error(
                        Throwable("Network somethings wrong!!! --- ${e.message}")
                    )
                }
            }
        }
    }

    abstract suspend fun onApi(page: Int?): Response<ListResponse<RequestType>>
    abstract suspend fun processResponse(request: ListResponse<RequestType>?): ListResponse<ResultType>?
}
