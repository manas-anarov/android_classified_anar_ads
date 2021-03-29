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

import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.anarads.model.PostList


class PostAdapter : ListAdapter<PostList, androidx.recyclerview.widget.RecyclerView.ViewHolder>(
    REPO_COMPARATOR
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        return PostViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        val repoItem = getItem(position)
        if (repoItem != null) {
            (holder as PostViewHolder).bind(repoItem)
        }



        holder.itemView.setOnClickListener{
//            Toast.makeText(holder.itemView.getContext(), repoItem.title, Toast.LENGTH_SHORT).show()

//            val intent = Intent(holder.itemView.getContext(), DetailActivity::class.java)
//            intent.putExtra("ID_POST", repoItem.id)
//
//
//            holder.itemView.getContext().startActivity(intent)

            val action = CategoryFragmentDirections.actionCategoryFragmentToBlankFragment(repoItem.id)
            holder.itemView.findNavController().navigate(action)
//            navController = Navigation.findNavController(holder.itemView)
//            navController!!.navigate(R.id.action_categoryFragment_to_blankFragment)
        }
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<PostList>() {
            override fun areItemsTheSame(oldItem: PostList, newItem: PostList): Boolean =
//                    oldItem.fullName == newItem.fullName
                    oldItem.title == newItem.title


            override fun areContentsTheSame(oldItem: PostList, newItem: PostList): Boolean =
                    oldItem == newItem
        }
    }
}
