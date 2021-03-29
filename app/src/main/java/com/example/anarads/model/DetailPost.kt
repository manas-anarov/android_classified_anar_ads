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

package com.example.anarads.model

import com.google.gson.annotations.SerializedName

data class DetailPost(
    @field:SerializedName("id") val id: Long,
    @field:SerializedName("description") val description: String,
    @field:SerializedName("title") val title: String,
    @field:SerializedName("price") val price: Int,
    @field:SerializedName("images_slider") val images_slider: List<ImagesSlider>
)
