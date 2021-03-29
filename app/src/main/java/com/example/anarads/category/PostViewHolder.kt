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

package com.example.anarads.category

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.anarads.R
import com.example.anarads.model.PostList
import com.squareup.picasso.Picasso


/**
 * View Holder for a [Repo] RecyclerView list item.
 */
class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val title: TextView = view.findViewById(R.id.post_title)
    private val description: TextView = view.findViewById(R.id.post_description)
    private val username: TextView = view.findViewById(R.id.post_username)
    private val image: ImageView = view.findViewById(R.id.category_item_image)


    private var repo: PostList? = null

    init {
        view.setOnClickListener {
//            repo?.url?.let { url ->
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                view.context.startActivity(intent)
//            }
        }
    }

    fun bind(repo: PostList?) {
        if (repo == null) {
            val resources = itemView.resources
            title.text = resources.getString(R.string.loading)
            description.visibility = View.GONE
            username.visibility = View.GONE
//            language.visibility = View.GONE
//            stars.text = resources.getString(R.string.unknown)
//            forks.text = resources.getString(R.string.unknown)
        } else {
            showRepoData(repo)
        }
    }

    private fun showRepoData(repo: PostList) {
        this.repo = repo

        title.text = repo.title
        description.text = repo.description
        val image_url = repo.image_first
        val salam = "http://192.168.0.105:8000" + image_url
//        if (repo.image_has){

            Picasso.get().load(salam).into(image);
//        }



        // if the description is missing, hide the TextView
//        var descriptionVisibility = View.GONE
//        if (repo.text != null) {
//            description.text = repo.text
//            descriptionVisibility = View.VISIBLE
//        }
//        description.visibility = descriptionVisibility
//
////        stars.text = repo.stars.toString()
//        stars.text = repo.category.toString()
//        id.text = repo.id.toString()
//        forks.text = repo.forks.toString()

        // if the language is missing, hide the label and the value
//        var languageVisibility = View.GONE
//        if (!repo.language.isNullOrEmpty()) {
//            val resources = this.itemView.context.resources
//            language.text = resources.getString(R.string.language, repo.language)
//            languageVisibility = View.VISIBLE
//        }
//        language.visibility = languageVisibility
    }

    companion object {
        fun create(parent: ViewGroup): PostViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.category_list_item, parent, false)
            return PostViewHolder(view)
        }
    }
}
