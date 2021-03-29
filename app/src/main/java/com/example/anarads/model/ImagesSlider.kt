package com.example.anarads.model

import com.google.gson.annotations.SerializedName


data class ImagesSlider(
        @field:SerializedName("id") val id: Long,
        @field:SerializedName("original") val original: String,
        @field:SerializedName("thumbnail") val title: String
)