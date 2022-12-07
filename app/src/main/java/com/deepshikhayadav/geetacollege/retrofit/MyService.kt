package com.deepshikhayadav.geetacollege.retrofit

import com.deepshikhayadav.geetacollege.local_db.entity.BlogPostResponse
import com.deepshikhayadav.geetacollege.local_db.entity.BlogResponse
import com.deepshikhayadav.geetacollege.local_db.entity.YogaResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface MyService {
    @GET("api/blog")
    fun getBlogs(): Call<BlogResponse>

    @GET("api/yoga")
    fun getYogas(): Call<YogaResponse>

    @Multipart
    @POST("api/blog")
    fun uploadBlog(
        @Part("heading") head: RequestBody,
        @Part("image\"; filename=\".png\" ") file: RequestBody,
        @Part("desc") desc: RequestBody,
        @Part("author") author: RequestBody
    ): Call<BlogPostResponse>

}