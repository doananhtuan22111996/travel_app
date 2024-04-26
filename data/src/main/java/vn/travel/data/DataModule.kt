package vn.travel.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.converter.gson.GsonConverterFactory
import vn.travel.data.local.PreferenceWrapper
import vn.travel.data.service.ApiService

object DataModule {
    val localModules = module(createdAtStart = true) {
        single { GsonConverterFactory.create() }
        single { PreferenceWrapper(androidContext()) }
    }

    val remoteModules = module(createdAtStart = true) {
        single { HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY) }
        single {
            Providers.provideOkHttpClient(
                get<HttpLoggingInterceptor>(),
                Providers.provideCache(androidContext())
            )
        }
        single {
            Providers.provideRetrofit<ApiService>(
                get<OkHttpClient>(), get<GsonConverterFactory>()
            )
        }
    }

    val repositoryModules = module {

    }
}
