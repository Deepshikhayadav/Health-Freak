package com.deepshikhayadav.geetacollege.retrofit.remote

import com.deepshikhayadav.geetacollege.local_db.entity.BlogResponse

interface MyRepository {
    fun fetchBlogs(/*apiKey: String,*/ onSuccess: (BlogResponse) -> Unit, onError: (String) -> Unit)

    fun getBlogsLocal(onSuccess: (BlogResponse?) -> Unit)
}