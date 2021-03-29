package com.example.anarads.category


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.anarads.data.PostRepository


open class ViewModelCategoryFragmentFactory(private val repository: PostRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryFragmentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CategoryFragmentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

