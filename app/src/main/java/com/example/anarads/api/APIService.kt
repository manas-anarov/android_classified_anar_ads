package com.example.anarads.api

import com.example.anarads.model.Token
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface APIService {

    @POST("accounts/api-token-auth/")
    @FormUrlEncoded
    fun accountsLogin(@Field("username") username: String,
                      @Field("password") password: String): Call<Token>

    @POST("accounts/register/")
    @FormUrlEncoded
    fun accountsRegister(@Field("username") username: String,
                        @Field("password") password: String,
                        @Field("email") email: String): Call<Token>



    @Multipart
    @POST("post/add/universal/")
    fun createCar(@Header("Authorization") BearerToken: String,
                   @Part("area") area: Int,
                   @Part("group") group: Int,
                   @Part("title") title: String,
                   @Part("description") description: String,
                   @Part("price") price: Int,
                   @Part("is_active") is_active: Boolean,
                   @Part("item_type") item_type: Int,
                   @Part("car_type") car_type: Int,
                   @Part("year") year: Int,
                   @Part image: List<MultipartBody.Part>): Call<Token>


    @Multipart
    @POST("post/add/universal/")
    fun createPostUni(@Header("Authorization") BearerToken: String,
                  @Part("area") area: Int,
                  @Part("group") group: Int,
                  @Part("title") title: String,
                  @Part("description") description: String,
                  @Part("price") price: Int,
                  @Part("is_active") is_active: Boolean,
                  @Part("item_type") item_type: Int,
                  @Part image: List<MultipartBody.Part>
    ): Call<Token>


    @POST("reklama/add/car/")
    @FormUrlEncoded
    fun createPostAuto1(@Header("Authorization") BearerToken: String,
                      @Field("item.area") area: Int,
                      @Field("item.group") group: Int,
                      @Field("item.title") title: String,
                      @Field("item.description") description: String,
                      @Field("item.price") price: Int,
                      @Field("item.is_active") is_active: Boolean,
                      @Field("item_type") item_type: Int): Call<Token>

    @POST("post/add/universal/")
    @FormUrlEncoded
    fun createPostAuto(@Header("Authorization") BearerToken: String,
                   @Field("area") area: Int,
                   @Field("group") group: Int,
                   @Field("title") title: String,
                   @Field("description") description: String,
                   @Field("price") price: Int,
                   @Field("is_active") is_active: Boolean,
                   @Field("item_type") item_type: Int,
                   @Field("car_type") car_type: Int,
                   @Field("year") year: Int): Call<Token>


    @POST("comments/create/?type=item")
    @FormUrlEncoded
    fun createComment(@Header("Authorization") BearerToken: String,
                      @Query("post_id") post_id: Int?,
//                    @Path("id") id: Int,
                    @Field("content") content: String): Call<Token>
}




object ApiUtils {

    val BASE_URL = "http://192.168.0.105:8000/api/v1/"

    val apiService: APIService
        get() = RetrofitClient.getClient(BASE_URL)!!.create(APIService::class.java)

}