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

data class PostList(
        @field:SerializedName("id") val id: Int,
        @field:SerializedName("name") val name: String,
        @field:SerializedName("title") val title: String,
        @field:SerializedName("item_type") val item_type: Int,
        @field:SerializedName("text") val text: String,
        @field:SerializedName("description") val description: String,
        @field:SerializedName("image_has") val image_has: Boolean,
        @field:SerializedName("image_first") val image_first: String

)
