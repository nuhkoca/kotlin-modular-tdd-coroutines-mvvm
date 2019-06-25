package com.mobilemovement.kotlintvmaze.di.module

import com.google.gson.FieldNamingPolicy.UPPER_CAMEL_CASE_WITH_SPACES
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mobilemovement.kotlintvmaze.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit.SECONDS
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        return GsonBuilder().apply {
            setFieldNamingPolicy(UPPER_CAMEL_CASE_WITH_SPACES)
            serializeNulls()
            setLenient()
        }.create()
    }

    @Provides
    @Singleton
    internal fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(Level.BODY)
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(DEFAULT_TIMEOUT, SECONDS)
            readTimeout(DEFAULT_TIMEOUT, SECONDS)
            writeTimeout(DEFAULT_TIMEOUT, SECONDS)

            interceptors().add(httpLoggingInterceptor)
        }.build()
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().apply {
            baseUrl(BuildConfig.baseUrl)
            addConverterFactory(GsonConverterFactory.create(gson))
            client(okHttpClient)
        }.build()
    }

    companion object {
        private const val DEFAULT_TIMEOUT = 60L
    }
}
