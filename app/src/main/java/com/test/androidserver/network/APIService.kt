package com.test.androidserver.network

import com.test.androidserver.model.CreatePostModel
import com.test.androidserver.model.SignupResponse
import com.test.androidserver.model.UserModel
import com.test.androidserver.model.post.AllPostModel
import com.test.androidserver.model.post.SetFavResponse
import com.test.androidserver.model.user.DpUploadResponse
import com.test.androidserver.model.user.UserProfile
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*






interface APIService {

    //for user registration
    @POST("signup.php")
    @FormUrlEncoded
    fun callSignup(@Field("email") email: String?,
                   @Field("password") password: String?,
                   @Field("full_name") full_name: String,
                   @Field("address") address: String,
                   @Field("city") city: String,
                   @Field("country") country: String,
                   @Field("postal_code") postcode: String,
                   @Field("ph_no") phone: String,
                   @Field("created_time") created_time: String): Call<SignupResponse>

    //
        //for user login
        @POST("login.php")
        @FormUrlEncoded
        fun callLogin(@Field("email") email: String?,
                                     @Field("password") password: String?): Call<UserModel>

    //for upload dp
    /*@Multipart
    @POST("dp_upload")
    fun uploadFile(@Part file: MultipartBody.Part,
                   @Part("file") name: RequestBody): Call<DpUploadResponse>*/
   /* @Multipart
    @POST("dpupload.php")
    //fun uploadImage(@Part("image") file: RequestBody, @Part("user_id") desc: RequestBody): Call<DpUploadResponse>
    fun uploadImage(@Part MultipartBody.Part file,
    @Part("user_id") RequestBody name)*/

    @Multipart
    @POST("dpupload.php")
    fun uploadImage(
            @Part("user_id") user_id: RequestBody,
            @Part file: MultipartBody.Part
    ): Call<DpUploadResponse>

    //fetching user profile
    @POST("user_profile.php")
    @FormUrlEncoded
    fun callProfile(@Field("user_id") user_id: String
                   ): Call<UserProfile>

    //fetching all posts
    @GET("post_listing.php")
    fun getAllposts(): Call<AllPostModel>

    //new Post

    @POST("create_post.php")
    @FormUrlEncoded
    fun callCreatePost(@Field("user_id") user_id: String?,
                   @Field("user_name") user_name: String?,
                   @Field("user_dp") full_name: String,
                   @Field("user_loc") address: String,
                   @Field("post_title") post_title: String,
                   @Field("post_desc") post_desc: String,
                   @Field("language_id") language_id: String,
                   @Field("favourites") favourites: String,
                   @Field("views") views: String,
                   @Field("comment_count") comment_count: String,
                   @Field("post_date") post_date:String,
                   @Field("priority") priority:String) : Call<CreatePostModel>

    //for update and remove favourites
    @POST("setfavourite.php")
    @FormUrlEncoded
    fun callSetFavourite(@Field("post_id") post_id: String?,
                       @Field("favourite_id") favourite_id: String?,
                         @Field("type") type:String)
                        : Call<SetFavResponse>

    //for set preview
    @POST("setpreview.php")
    @FormUrlEncoded
    fun callSetPreview(@Field("post_id") post_id: String?,
                         @Field("preview_id") preview_id: String)

            : Call<SetFavResponse>


}