package com.example.anarads.uploader

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anarads.R
//import com.example.anarads.detail.DetailActivity
import kotlinx.android.synthetic.main.upload_list_item.view.*

class UploadAdapter(val items : ArrayList<Uri?>) : RecyclerView.Adapter<ViewHolder>() {


    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

    fun getAllItems(): ArrayList<Uri?> {
        return items
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.upload_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.tvAnimalType?.text = items.get(position).toString()
        holder.ivAnimal?.setImageURI(items.get(position))

        holder.ivDelete_button.setOnClickListener{

            items.removeAt(position)
            this.notifyDataSetChanged()
        }
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val ivAnimal = view.iv_animals
    val ivDelete_button = view.li_delete_buttons
}