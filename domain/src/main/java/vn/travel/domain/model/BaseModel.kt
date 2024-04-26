package vn.travel.domain.model

import java.net.HttpURLConnection

open class BaseModel

sealed class ResultModel<out R> {
    data class Success<out R>(val data: R? = null) : ResultModel<R>()

    data class AppException(val type: TypeException, val message: String? = "") :
        ResultModel<Nothing>()

    data object Loading : ResultModel<Nothing>()
    data object Done : ResultModel<Nothing>()
}

sealed class TypeException(open val httpCode: Int?) {
    data class Network(override val httpCode: Int? = HttpURLConnection.HTTP_OK) :
        TypeException(httpCode)

    data object Local : TypeException(httpCode = null)
}