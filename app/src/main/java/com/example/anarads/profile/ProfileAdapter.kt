package com.example.anarads.profile

import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.anarads.category.CategoryFragmentDirections
import com.example.anarads.category.PostViewHolder

//import com.example.anarads.detail.DetailActivity
import com.example.anarads.model.PostList


class ProfileAdapter : ListAdapter<PostList, androidx.recyclerview.widget.RecyclerView.ViewHolder>(
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

//        var navController: NavController? = null

        holder.itemView.setOnClickListener{
//            Toast.makeText(holder.itemView.getContext(), repoItem.title, Toast.LENGTH_SHORT).show()

//            val intent = Intent(holder.itemView.getContext(), DetailActivity::class.java)
//            intent.putExtra("ID_POST", repoItem.id)
//
//
//            holder.itemView.getContext().startActivity(intent)

//            val action = ProfileFragmentDirections.actionProfileFragmentToEditCar(repoItem.id)
//            holder.itemView.findNavController().navigate(action)

            if(repoItem.item_type == 1){
                val action = ProfileFragmentDirections.actionProfileFragmentToEditPost(repoItem.id)
                holder.itemView.findNavController().navigate(action)
            }
            if(repoItem.item_type == 2){
                val action = ProfileFragmentDirections.actionProfileFragmentToEditCar(repoItem.id)
                holder.itemView.findNavController().navigate(action)
            }




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