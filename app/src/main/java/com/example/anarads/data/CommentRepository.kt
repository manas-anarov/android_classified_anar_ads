package com.example.anarads.data


import android.content.Context
import android.util.Log


import com.example.anarads.api.IN_QUALIFIER
import com.example.anarads.api.PostService
import com.example.anarads.model.Comment
import com.example.anarads.model.CommentResult
import com.example.anarads.model.Repo
//import com.example.anarads.model.RepoSearchResult

import com.example.anarads.utils.GetSetData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import retrofit2.HttpException
import java.io.IOException

private const val GITHUB_STARTING_PAGE_INDEX = 1


@ExperimentalCoroutinesApi
class CommentRepository(private val service: PostService) {


    private val inCommentMemoryCache = mutableListOf<Comment>()


    private val commentResults = ConflatedBroadcastChannel<CommentResult>()

    private var lastRequestedPage = GITHUB_STARTING_PAGE_INDEX

    private var isRequestInProgress = false

    private var has_more = false



    suspend fun getCommentResultStream(post_id:Int): Flow<CommentResult> {
        Log.d("GithubRepository", "New query: s")
        lastRequestedPage = 1
        inCommentMemoryCache.clear()
        requestCommentAndSaveData(post_id)

        return commentResults.asFlow()
    }



    suspend fun requestMore(post_id: Int, query: String) {
        if (isRequestInProgress) return
        if (has_more){
            val successful = requestCommentAndSaveData(post_id)
            if (successful) {
                lastRequestedPage++
            }
        }
    }







    private suspend fun requestCommentAndSaveData(post_id:Int): Boolean {
        isRequestInProgress = true
        var successful = false


        try {
            val response = service.commentList(post_id, lastRequestedPage, NETWORK_PAGE_SIZE)
            Log.d("GithubRepository", "response $response")
            val repos = response.items ?: emptyList()

            val salam = response.nextPage
            if (salam != null){
                has_more = true
            }
            if (salam == null){
                has_more = false
            }


            inCommentMemoryCache.addAll(repos)
            val reposByName = reposByNameComment()
            commentResults.offer(CommentResult.Success(reposByName))
            successful = true
        } catch (exception: IOException) {
            commentResults.offer(CommentResult.Error(exception))
        } catch (exception: HttpException) {
            commentResults.offer(CommentResult.Error(exception))
        }
        isRequestInProgress = false
        return successful
    }


    private fun reposByNameComment(): List<Comment> {
        return inCommentMemoryCache.filter {
            true
        }.sortedWith(compareByDescending<Comment> { it.content }.thenBy { it.content })
    }





    companion object {
        private const val NETWORK_PAGE_SIZE = 5
    }
}
