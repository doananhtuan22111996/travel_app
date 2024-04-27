package vn.travel.data.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import vn.travel.data.model.AttractionRaw
import vn.travel.data.model.ListResponse

interface ApiService {

    @Headers("Accept:application/json; charset=UTF-8")
    @GET("open-api/{lang}/Attractions/All")
    suspend fun attractions(
        @Path("lang") lang: String, @Query("page") page: Int
    ): Response<ListResponse<AttractionRaw>>
}
