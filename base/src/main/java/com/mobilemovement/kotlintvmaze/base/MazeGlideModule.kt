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
package com.mobilemovement.kotlintvmaze.base

import android.content.Context
import android.graphics.Bitmap.CompressFormat
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.Excludes
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpLibraryGlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.DecodeFormat.PREFER_ARGB_8888
import com.bumptech.glide.load.engine.DiskCacheStrategy.RESOURCE
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.bumptech.glide.signature.ObjectKey
import okhttp3.OkHttpClient
import java.io.InputStream
import java.util.concurrent.TimeUnit.SECONDS

@GlideModule(glideName = "MazeGlide")
@Excludes(value = [OkHttpLibraryGlideModule::class])
class MazeGlideModule : AppGlideModule() {

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        val client = OkHttpClient.Builder()
            .readTimeout(DEFAULT_TIMEOUT, SECONDS)
            .connectTimeout(DEFAULT_TIMEOUT, SECONDS)
            .build()

        val factory = OkHttpUrlLoader.Factory(client)

        glide.registry.replace(GlideUrl::class.java, InputStream::class.java, factory)
    }

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        val memoryCacheSizeBytes = DEFAULT_MEMORY_CACHE_SIZE
        builder.apply {
            setMemoryCache(LruResourceCache(memoryCacheSizeBytes.toLong()))
            setDiskCache(InternalCacheDiskCacheFactory(context, memoryCacheSizeBytes.toLong()))
            setDefaultRequestOptions(requestOptions())
        }
    }

    private fun requestOptions(): RequestOptions {
        return RequestOptions().apply {
            signature(ObjectKey(System.currentTimeMillis() / DEFAULT_SIGNATURE_OBJECT)) // 1 week cache
            centerCrop()
            dontAnimate()
            override(SIZE_ORIGINAL)
            encodeFormat(CompressFormat.PNG)
            encodeQuality(DEFAULT_ENCODE_QUALITY)
            diskCacheStrategy(RESOURCE)
            format(PREFER_ARGB_8888)
            skipMemoryCache(true)
        }
    }

    companion object {
        private const val DEFAULT_TIMEOUT = 60L
        private const val DEFAULT_MEMORY_CACHE_SIZE = 1024 * 1024 * 300 // 300mb cache
        private const val DEFAULT_SIGNATURE_OBJECT = 168 * 60 * 60 * 1000
        private const val DEFAULT_ENCODE_QUALITY = 100
    }
}
