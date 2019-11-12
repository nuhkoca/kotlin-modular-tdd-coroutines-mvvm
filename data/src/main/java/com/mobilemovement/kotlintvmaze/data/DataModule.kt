package com.mobilemovement.kotlintvmaze.data

import android.content.Context
import com.google.gson.FieldNamingPolicy.UPPER_CAMEL_CASE_WITH_SPACES
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mobilemovement.kotlintvmaze.base.util.checkMainThread
import dagger.Lazy
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit.SECONDS
import javax.inject.Singleton

@Module
object DataModule {

    private const val DEFAULT_TIMEOUT = 60L
    private const val CACHE_SIZE = 10 * 1024 * 1024

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
        return HttpLoggingInterceptor().apply { level = Level.BODY }
    }

    @Provides
    internal fun provideCache(context: Context): Cache {
        return checkMainThread { Cache(context.cacheDir, CACHE_SIZE.toLong()) }
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(
        cache: Cache,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return checkMainThread {
            OkHttpClient.Builder().apply {
                cache(cache)
                connectTimeout(DEFAULT_TIMEOUT, SECONDS)
                readTimeout(DEFAULT_TIMEOUT, SECONDS)
                writeTimeout(DEFAULT_TIMEOUT, SECONDS)

                interceptors().add(httpLoggingInterceptor)
            }.build()
        }
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(gson: Gson, okHttpClient: Lazy<OkHttpClient>): Retrofit {
        return Retrofit.Builder().apply {
            baseUrl(BuildConfig.baseUrl)
            addConverterFactory(GsonConverterFactory.create(gson))
            delegatingCallFactory(okHttpClient)
        }.build()
    }

    @Provides
    @Singleton
    internal fun provideMazeService(retrofit: Retrofit): MazeService = retrofit.create()
}
