package com.example.recipefinder.network.di

import com.example.recipefinder.network.RestApiService
import com.example.recipefinder.network.interceptor.ApiKeyInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RestApiModule {
    private const val BASE_URL = "https://api.spoonacular.com"
    private const val READ_TIME = 10L
    private const val WRITE_TIME = 10L
    private const val CONNECTION_TIME = 5L
    private const val API_KEY = "06add25c93714538a539fd6244915f9b"

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient
            .Builder()
            .connectTimeout(CONNECTION_TIME, TimeUnit.SECONDS)
            .readTimeout(READ_TIME, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIME, TimeUnit.SECONDS)
            .addInterceptor(ApiKeyInterceptor(API_KEY))
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun providesRestApiService(retrofit: Retrofit): RestApiService {
        return retrofit.create(RestApiService::class.java)
    }
}
