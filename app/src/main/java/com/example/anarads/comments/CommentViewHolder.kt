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


package com.example.anarads.comments
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.anarads.R
import com.example.anarads.model.Comment


/**
 * View Holder for a [Repo] RecyclerView list item.
 */
class CommentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//    private val name: TextView = view.findViewById(R.id.repo_name)
    private val description: TextView = view.findViewById(R.id.comment_description)
    private val date: TextView = view.findViewById(R.id.comment_date)
    private val username: TextView = view.findViewById(R.id.comment_username)
//    private val language: TextView = view.findViewById(R.id.repo_language)
//    private val forks: TextView = view.findViewById(R.id.repo_forks)
//    private val id: TextView = view.findViewById(R.id.repo_id)

    private var repo: Comment? = null

    init {
        view.setOnClickListener {
//            repo?.url?.let { url ->
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                view.context.startActivity(intent)
//            }
        }
    }

    fun bind(repo: Comment?) {
        if (repo == null) {
            val resources = itemView.resources
            description.text = resources.getString(R.string.loading)
            description.visibility = View.GONE

        } else {
            showRepoData(repo)
        }
    }

    private fun showRepoData(repo: Comment) {
        this.repo = repo

        description.text = repo.content

        // if the description is missing, hide the TextView
        var descriptionVisibility = View.GONE
        if (repo.content != null) {
            description.text = repo.content
            descriptionVisibility = View.VISIBLE
        }
        description.visibility = descriptionVisibility

//        stars.text = repo.stars.toString()
        username.text = repo.user
        date.text = repo.timestamp
//        id.text = repo.id.toString()
//        forks.text = repo.forks.toString()

        // if the language is missing, hide the label and the value
        var languageVisibility = View.GONE
//        if (!repo.language.isNullOrEmpty()) {
//            val resources = this.itemView.context.resources
//            language.text = resources.getString(R.string.language, repo.language)
//            languageVisibility = View.VISIBLE
//        }
//        language.visibility = languageVisibility
    }

    companion object {
        fun create(parent: ViewGroup): CommentViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.comment_item, parent, false)
            return CommentViewHolder(view)
        }
    }
}
