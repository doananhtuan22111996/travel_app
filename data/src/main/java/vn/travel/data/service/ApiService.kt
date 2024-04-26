package vn.travel.data.service

import retrofit2.Response
import retrofit2.http.POST
import vn.travel.data.model.ObjectResponse

interface ApiService {

    @POST("login")
    suspend fun login(): Response<ObjectResponse<Nothing>>
}
