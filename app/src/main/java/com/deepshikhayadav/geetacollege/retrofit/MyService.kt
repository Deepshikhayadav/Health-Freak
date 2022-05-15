package com.deepshikhayadav.geetacollege.retrofit

import com.deepshikhayadav.geetacollege.local_db.entity.BlogResponse
import retrofit2.Call
import retrofit2.http.GET

interface MyService {
    @GET("api/blog")
    fun getBlogs(): Call<BlogResponse>
}