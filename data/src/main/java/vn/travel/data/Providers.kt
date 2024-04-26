package vn.travel.data

import android.content.Context
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import vn.travel.data.network.NullOrEmptyConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

internal object Providers {
    fun provideCache(context: Context): Cache {
        val file = File(context.cacheDir, Config.Cache.CACHE_FILE_NAME)
        val isSuccess = file.mkdirs()
        return if (isSuccess) {
            Cache(file, Config.Cache.CACHE_FILE_SIZE)
        } else Cache(context.cacheDir, Config.Cache.CACHE_FILE_SIZE)
    }

    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        cache: Cache,
    ): OkHttpClient {
        val builder =
            OkHttpClient.Builder().readTimeout(Config.TimeOut.TIMEOUT_READ_SECOND, TimeUnit.SECONDS)
                .connectTimeout(Config.TimeOut.TIMEOUT_CONNECT_SECOND, TimeUnit.SECONDS)
                .writeTimeout(Config.TimeOut.TIMEOUT_WRITE_SECOND, TimeUnit.SECONDS)
        if (Config.isDebug) {
            builder.addInterceptor(httpLoggingInterceptor)
        } else {
            builder.cache(cache)
        }
        return builder.build()
    }

    inline fun <reified T> provideRetrofit(
        okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory
    ): T {
        val retrofit = Retrofit.Builder().baseUrl(Config.mainDomain).client(okHttpClient)
            .addConverterFactory(NullOrEmptyConverterFactory())
            .addConverterFactory(gsonConverterFactory).build()
        return retrofit.create(T::class.java)
    }
}
