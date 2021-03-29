package com.example.anarads.model

import com.google.gson.annotations.SerializedName

data class City(
        @field:SerializedName("id") val id: Long,
        @field:SerializedName("name_city") val name_city: String

){
    override fun toString(): String {
        return name_city
    }
}