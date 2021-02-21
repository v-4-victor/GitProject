/*
 * Copyright 2019, The Android Open Source Project
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
 *
 */

package com.example.android.marsrealestate.network

import com.example.stonks.network.MarsProperty
import com.example.stonks.network.PriceInfo
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://finnhub.io/api/v1/"
private const val TOKEN = "c0mmsm748v6tkq136co0"
private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

enum class MarsApiFilter(val value: String) {
    SHOW_RENT("rent"),
    SHOW_BUY("buy"),
    SHOW_ALL("all") }

private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()


interface MarsApiService {
    @GET("stock/symbol")
    suspend fun getProperties(@Query("exchange") type: String = "US", @Query("token") token: String = TOKEN): List<MarsProperty>
    @GET("index/constituents")
    suspend fun getTop(@Query("symbol") symbol: String = "^DJI", @Query("token") token: String = TOKEN): List<String>
    @GET("quote")
    suspend fun getPrice(@Query("symbol") symbol: String, @Query("token") token: String = TOKEN): PriceInfo
}


object MarsApi {
    val retrofitService: MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }
}