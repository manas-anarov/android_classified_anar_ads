package com.example.anarads.category

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import com.example.anarads.data.PostRepository
import com.example.anarads.model.PostResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import retrofit2.http.QueryMap



@ExperimentalCoroutinesApi
class CategoryFragmentViewModel(private val repository: PostRepository) : ViewModel() {


    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }

    private var token = ""


    private val queryLiveData = MutableLiveData<HashMap<String,String>>()

    val repoResult: LiveData<PostResult> = queryLiveData.switchMap { queryString ->
        liveData {
            val repos = repository.getSearchResultNew(queryString).asLiveData(Dispatchers.Main)
            emitSource(repos)
        }
    }


    val repoResultProfile: LiveData<PostResult> = queryLiveData.switchMap { queryString ->
        liveData {
            val repos = repository.getSearchResultProfile(token, queryString).asLiveData(Dispatchers.Main)
            emitSource(repos)
        }
    }



    fun setToken(salam: String) {
        token = salam

    }










    fun searchRepo(queryString: HashMap<String,String>) {

        queryLiveData.postValue(queryString)
    }

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {

        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
            val immutableQuery = queryLiveData.value
            if (immutableQuery != null) {
                viewModelScope.launch {
                    repository.requestMore(immutableQuery)
                }
            }
        }
    }
}