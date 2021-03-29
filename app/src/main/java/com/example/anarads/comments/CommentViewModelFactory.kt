package com.example.anarads.comments
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.anarads.data.CommentRepository



open class CommentViewModelFactory(private val repository: CommentRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommentsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CommentsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

