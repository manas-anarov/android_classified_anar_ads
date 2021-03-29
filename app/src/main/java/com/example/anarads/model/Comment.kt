package com.example.anarads.model


import com.google.gson.annotations.SerializedName

data class Comment(
        @field:SerializedName("id") val id: Long,
        @field:SerializedName("content") val content: String,
        @field:SerializedName("user") val user: String,
        @field:SerializedName("timestamp") val timestamp: String
)
