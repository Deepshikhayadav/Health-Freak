package com.deepshikhayadav.geetacollege.retrofit.remote

import com.deepshikhayadav.geetacollege.local_db.entity.BlogPostResponse
import com.deepshikhayadav.geetacollege.local_db.entity.BlogResponse
import com.deepshikhayadav.geetacollege.local_db.entity.YogaResponse
import java.io.File

interface MyRepository {
    fun fetchBlogs(/*apiKey: String,*/ onSuccess: (BlogResponse) -> Unit, onError: (String) -> Unit)

    fun getBlogsLocal(onSuccess: (BlogResponse?) -> Unit)

    fun fetchYogas(/*apiKey: String,*/ onSuccess: (YogaResponse) -> Unit, onError: (String) -> Unit)

    fun getYogasLocal(onSuccess: (YogaResponse?) -> Unit)

    fun uploadBlog(head: String, desc: String, author: String, file: File,
                   onSuccess: (BlogPostResponse) -> Unit, onError: (String) -> Unit)
}