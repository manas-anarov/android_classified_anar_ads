package com.example.anarads.model

import com.google.gson.annotations.SerializedName

data class Brand(
        @field:SerializedName("id") val id: Long,
        @field:SerializedName("name_brand") val name_brand: String

){
    override fun toString(): String {
        return name_brand
    }
}

//val brand_types = arrayOf("Honda", "Toyota", "BMW")