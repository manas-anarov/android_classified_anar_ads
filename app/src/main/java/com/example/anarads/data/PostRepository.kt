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

//package com.example.android.codelabs.paging.data
package com.example.anarads.data

import android.content.Context
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext

import com.example.anarads.api.IN_QUALIFIER
import com.example.anarads.api.PostResponse
import com.example.anarads.api.PostService
import com.example.anarads.model.*
import com.example.anarads.utils.GetSetData

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import retrofit2.HttpException
import retrofit2.http.QueryMap
import java.io.IOException

// GitHub page API is 1 based: https://developer.github.com/v3/#pagination
private const val GITHUB_STARTING_PAGE_INDEX = 1

/**
 * Repository class that works with local and remote data sources.
 */
@ExperimentalCoroutinesApi
class PostRepository(private val service: PostService) {

    private val inMemoryCache = mutableListOf<PostList>()

    private val searchResults = ConflatedBroadcastChannel<PostResult>()

    private var lastRequestedPage = GITHUB_STARTING_PAGE_INDEX

    private var isRequestInProgress = false
    private var has_more = false


    suspend fun getSearchResultStream(query: String): Flow<PostResult> {
        Log.d("PostRepository", "New query: $query")
        lastRequestedPage = 1
        inMemoryCache.clear()
        requestAndSaveData(query)

        return searchResults.asFlow()
    }

    suspend fun getSearchResultFirst(): Flow<PostResult> {
        Log.d("getSearchResultFirst", "New query: getSearchResultFirst")
        lastRequestedPage = 1
        inMemoryCache.clear()
        val filter = HashMap<String,String>()
        filter.put("sa", "as")
        requestAndSaveDataFirst(filter)

        return searchResults.asFlow()
    }

    suspend fun getSearchResultNew(@QueryMap filter:HashMap<String,String>): Flow<PostResult> {
        Log.d("getSearchResultNew", "New query: $filter")
        lastRequestedPage = 1
        inMemoryCache.clear()
        requestAndSaveDataFirst(filter)

        return searchResults.asFlow()
    }

    suspend fun getSearchResultProfile(token: String, @QueryMap filter:HashMap<String,String>): Flow<PostResult> {
        Log.d("getSearchResultNew", "New query: $filter")
        lastRequestedPage = 1
        inMemoryCache.clear()
        requestAndSaveDataProfile(token, filter)

        return searchResults.asFlow()
    }






    suspend fun requestMore(filter:HashMap<String,String>) {
        if (isRequestInProgress) return
        if (has_more){
            val successful = requestAndSaveDataFirst(filter)
            if (successful) {
                lastRequestedPage++
            }
        }
    }

    suspend fun retry(query: String) {
        if (isRequestInProgress) return
        requestAndSaveData(query)
    }

    private suspend fun requestAndSaveData(query: String): Boolean {
        isRequestInProgress = true
        var successful = false

        val apiQuery = query + IN_QUALIFIER
        try {
            val response = service.searchRepos(apiQuery, lastRequestedPage, NETWORK_PAGE_SIZE)
            Log.d("PostRepository", "response $response")
            val repos = response.items ?: emptyList()

            val salam = response.nextPage
            if (salam != null){
                has_more = true
            }
            if (salam == null){
                has_more = false
            }


            inMemoryCache.addAll(repos)
            val reposByName = reposByName(query)
            searchResults.offer(PostResult.Success(reposByName))
            successful = true
        } catch (exception: IOException) {
            searchResults.offer(PostResult.Error(exception))
        } catch (exception: HttpException) {
            searchResults.offer(PostResult.Error(exception))
        }
        isRequestInProgress = false
        return successful
    }




    suspend fun requestAndSaveDataFirst(@QueryMap filter:HashMap<String,String>): Boolean {
        isRequestInProgress = true
        var successful = false

//        val apiQuery = query + IN_QUALIFIER
        try {
            var response = PostResponse()

            response = service.categoryList(filter, lastRequestedPage, NETWORK_PAGE_SIZE)


            Log.d("PostRepository", "response $response")
            val repos = response.items ?: emptyList()

            val salam = response.nextPage
            if (salam != null){
                has_more = true
            }
            if (salam == null){
                has_more = false
            }


            inMemoryCache.addAll(repos)
            val reposByName = reposByNameFirst()
            searchResults.offer(PostResult.Success(reposByName))
            successful = true
        } catch (exception: IOException) {
            searchResults.offer(PostResult.Error(exception))
        } catch (exception: HttpException) {
            searchResults.offer(PostResult.Error(exception))
        }
        isRequestInProgress = false
        return successful
    }


    suspend fun requestAndSaveDataProfile(token_ex_ap: String, @QueryMap filter:HashMap<String,String>): Boolean {
        isRequestInProgress = true
        var successful = false

//        val token_ex_ap: String = GetSetData.getMySavedToken(context)

//        val apiQuery = query + IN_QUALIFIER
        try {
            var response = PostResponse()

            response = service.profileList(token_ex_ap, filter, lastRequestedPage, NETWORK_PAGE_SIZE)


            Log.d("PostRepository", "response $response")
            val repos = response.items ?: emptyList()

            val salam = response.nextPage
            if (salam != null){
                has_more = true
            }
            if (salam == null){
                has_more = false
            }


            inMemoryCache.addAll(repos)
            val reposByName = reposByNameFirst()
            searchResults.offer(PostResult.Success(reposByName))
            successful = true
        } catch (exception: IOException) {
            searchResults.offer(PostResult.Error(exception))
        } catch (exception: HttpException) {
            searchResults.offer(PostResult.Error(exception))
        }
        isRequestInProgress = false
        return successful
    }









    private fun reposByName(query: String): List<PostList> {

        return inMemoryCache.filter {
            it.title.contains(
                    query, true) ||
                    (it.title != null && it.title.contains(query, true)
            )
        }.sortedWith(compareByDescending<PostList> { it.title }.thenBy { it.title })
    }

    private fun reposByNameFirst(): List<PostList> {
        return inMemoryCache.filter {
            true
        }.sortedWith(compareByDescending<PostList> { it.title }.thenBy { it.title })
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 5
    }
}
