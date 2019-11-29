/*
 * Copyright 2019 nuhkoca.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mobilemovement.kotlintvmaze.data

import android.content.Context
import com.google.gson.FieldNamingPolicy.UPPER_CAMEL_CASE_WITH_SPACES
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mobilemovement.kotlintvmaze.base.util.checkMainThread
import dagger.Lazy
import dagger.Module
import dagger.Provides
import java.util.concurrent.TimeUnit.SECONDS
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlin.annotation.AnnotationRetention.BINARY
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@Retention(BINARY)
@Qualifier
private annotation class InternalApi

@Module
object DataModule {

    private const val DEFAULT_TIMEOUT = 60L
    private const val CACHE_SIZE = 10 * 1024 * 1024

    @InternalApi
    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        return GsonBuilder().apply {
            setFieldNamingPolicy(UPPER_CAMEL_CASE_WITH_SPACES)
            serializeNulls()
            setLenient()
        }.create()
    }

    @InternalApi
    @Provides
    @Singleton
    internal fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply { level = Level.BODY }
    }

    @InternalApi
    @Provides
    internal fun provideCache(context: Context): Cache {
        return checkMainThread { Cache(context.cacheDir, CACHE_SIZE.toLong()) }
    }

    @InternalApi
    @Provides
    @Singleton
    internal fun provideOkHttpClient(
        @InternalApi cache: Cache,
        @InternalApi httpLoggingInterceptor: HttpLoggingInterceptor
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
    internal fun provideRetrofit(
        @InternalApi gson: Gson,
        @InternalApi okHttpClient: Lazy<OkHttpClient>
    ): Retrofit {
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
