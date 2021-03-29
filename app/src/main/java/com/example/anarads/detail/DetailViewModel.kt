package com.example.anarads.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anarads.model.DetailPost
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

class DetailViewModel : ViewModel() {
    val productDetails = MutableLiveData<DetailPost>()

    fun fetchProductDetails(id_post: Int) {
        viewModelScope.launch(Dispatchers.Default) {
//            val json = URL("https://gist.githubusercontent.com/danielmalone/df34a33a06e985d85f2ba7f6e635c600/raw/df0aeaea2def4aa1c9b0f266d032b6733c3fbaaa/shopping_products.json").readText()
            val json = URL("http://192.168.0.105:8000/api/v1/post/detail/$id_post/").readText()
            val list = Gson().fromJson(json, DetailPost::class.java)
//            val product = list { it.title == productTitle }

            productDetails.postValue(list)
        }
    }
}