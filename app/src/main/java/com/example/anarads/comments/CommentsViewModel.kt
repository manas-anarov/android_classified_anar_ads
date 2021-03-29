package com.example.anarads.comments

import android.app.Application
import androidx.lifecycle.*
import com.example.anarads.data.CommentRepository
import com.example.anarads.model.CommentResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch


@ExperimentalCoroutinesApi
class CommentsViewModel(private val repository: CommentRepository) :ViewModel() {


    private var post_id_my = 1

    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }

    private val queryLiveData = MutableLiveData<String>()
    //    val token_ex_ap: String = com.example.android.codelabs.paging.utils.GetSetData.getMySavedToken(application)
    val repoResult: LiveData<CommentResult> = queryLiveData.switchMap {
        liveData {
            val repos = repository.getCommentResultStream(post_id_my).asLiveData(Dispatchers.Main)
            emitSource(repos)
        }
    }

    /**
     * Search a repository based on a query string.
     */
    fun searchRepo() {
        queryLiveData.postValue("s")
    }

    fun setPostId(post_id: Int?) {
        if (post_id != null) {
            post_id_my = post_id
        }

    }

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
            val immutableQuery = queryLiveData.value
            if (immutableQuery != null) {
                viewModelScope.launch {
                    repository.requestMore(post_id_my,immutableQuery)
                }
            }
        }
    }
}