/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.anarads.api

import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.*

const val IN_QUALIFIER = "in:content"


interface PostService {




    @GET("post/list/")
    suspend fun searchRepos(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): PostResponse

    @GET("post/list/")
    suspend fun searchReposFirst(
            @Query("page") page: Int,
            @Query("per_page") itemsPerPage: Int
    ): PostResponse


    @GET("post/list/")
    suspend fun categoryList(
            @QueryMap filter: HashMap <String,String>,
            @Query("page") page: Int,
            @Query("per_page") itemsPerPage: Int
    ): PostResponse

    @GET("post/profile/list/")
    suspend fun profileList(
        @Header("Authorization") BearerToken: String,
        @QueryMap filter: HashMap <String,String>,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): PostResponse

    @GET("post/list/")
    suspend fun categoryListAll(
            @Query("page") page: Int,
            @Query("per_page") itemsPerPage: Int
    ): PostResponse

    @GET("comments/post/{id}")
    suspend fun commentList(
//            @Query("q") query: String,
            @Path("id") id: Int,
            @Query("page") page: Int,
            @Query("per_page") itemsPerPage: Int
    ): CommentsResponse




    companion object {
//        private const val BASE_URL = "http://192.168.0.103:8000/api/v1/post/"
//        private const val BASE_URL = "http://192.168.0.104:8000/api/v1/reklama/profile/"
        private const val BASE_URL = "http://192.168.0.105:8000/api/v1/"

        fun create(): PostService {
            val logger = HttpLoggingInterceptor()
            logger.level = Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
//                    .connectionSpecs(Arrays.asList(ConnectionSpec.COMPATIBLE_TLS))
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PostService::class.java)
        }
    }
}