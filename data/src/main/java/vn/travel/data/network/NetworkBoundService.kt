package vn.travel.data.network

import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import timber.log.Timber
import vn.travel.data.model.ObjectResponse
import vn.travel.domain.model.ResultModel
import vn.travel.domain.model.TypeException

abstract class NetworkBoundService<RequestType, ResultType>(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    private val tag = NetworkBoundService::class.java.name

    /**
     * Follow Best Practice
     * https://github.com/android/architecture-components-samples/blob/main/GithubBrowserSample/app/src/main/java/com/android/example/github/repository/NetworkBoundResource.kt
     * */
    fun build() = flow {
        emit(ResultModel.Loading)
        emit(fetchFromNetwork())
        emit(ResultModel.Done)
    }.flowOn(dispatcher)

    /**
     * This Func handle response from Network [fetchFromNetwork].
     */
    private suspend fun fetchFromNetwork(): ResultModel<ResultType>? {
        Timber.i(tag, "Fetch data from network")
        val apiResponse = onApi()
        val result: ResultModel<ResultType> = if (apiResponse.isSuccessful) {
            Timber.d("Response Api: ${apiResponse.body().toString()}")
            val body = apiResponse.body()
            processResponse(body)
        } else {
            Timber.e("Response Error: ${Gson().toJson(apiResponse.errorBody())}")
            Timber.e("Response Error: ${Gson().toJson(apiResponse.body())}")
            try {
                // TODO
                Gson().fromJson(
                    apiResponse.errorBody()?.toString(), ResultModel.AppException::class.java
                ) ?: ResultModel.AppException(
                    type = TypeException.Network(httpCode = apiResponse.code()),
                    message = "Network Somethings wrong!"
                )
            } catch (e: Exception) {
                ResultModel.AppException(
                    type = TypeException.Network(httpCode = apiResponse.code()), message = e.message
                )
            }
        }
        return result
    }

    abstract suspend fun onApi(): Response<ObjectResponse<RequestType>>

    abstract suspend fun processResponse(request: ObjectResponse<RequestType>?): ResultModel.Success<ResultType>

}