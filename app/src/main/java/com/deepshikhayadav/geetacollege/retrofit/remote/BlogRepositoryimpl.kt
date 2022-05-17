package com.deepshikhayadav.geetacollege.retrofit.remote

import android.util.Log
import com.deepshikhayadav.geetacollege.local_db.dao.MyDao
import com.deepshikhayadav.geetacollege.local_db.entity.BlogPostResponse
import com.deepshikhayadav.geetacollege.local_db.entity.BlogResponse
import com.deepshikhayadav.geetacollege.local_db.entity.YogaResponse
import com.deepshikhayadav.geetacollege.retrofit.MyService
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class BlogRepositoryimpl (
    private val myDao: MyDao,
    private val myService: MyService
) : MyRepository {

    override fun fetchBlogs(onSuccess: (BlogResponse) -> Unit, onError: (String) -> Unit){
        val response = myService.getBlogs(/*apiKey*/)
        response.enqueue(object : Callback<BlogResponse> {
            override fun onResponse(call: Call<BlogResponse>, response: Response<BlogResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    Thread {
                        myDao.insertBlogs(response.body()!!)
                        onSuccess(response.body()!!)
                    }.start()
                } else {
                    onError(response.message())
                }
            }

            override fun onFailure(call: Call<BlogResponse>, throwable: Throwable) {
                onError(throwable.localizedMessage ?: "Something went wrong")
            }
        })
    }

    override fun getBlogsLocal(onSuccess: (BlogResponse?) -> Unit) {

        Thread {
            onSuccess(myDao.getBlogs())
        }.start()
    }

    override fun fetchYogas(onSuccess: (YogaResponse) -> Unit, onError: (String) -> Unit) {
        val response = myService.getYogas()
        response.enqueue(object : Callback<YogaResponse> {
            override fun onResponse(call: Call<YogaResponse>, response: Response<YogaResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    Thread {
                        myDao.insertYoga(response.body()!!)
                        onSuccess(response.body()!!)
                    }.start()
                } else {
                    onError(response.message())
                }
            }

            override fun onFailure(call: Call<YogaResponse>, throwable: Throwable) {
                onError(throwable.localizedMessage ?: "Something went wrong")
            }
        })
    }

    override fun getYogasLocal(onSuccess: (YogaResponse?) -> Unit) {
        Thread {
            onSuccess(myDao.getYogas())
        }.start()
    }

    override fun uploadBlog(
        head1: String,
        desc1: String,
        author1: String,
        file1: File,
        onSuccess: (BlogPostResponse) -> Unit,
        onError: (String) -> Unit
    ) {

        val img =
            RequestBody.create(MediaType.parse( "image/png"), file1)
        val head: RequestBody = RequestBody.create(MediaType.parse("text/plain"), head1)
        val desc: RequestBody = RequestBody.create(MediaType.parse("text/plain"), desc1)
        val author: RequestBody = RequestBody.create(MediaType.parse("text/plain"), author1)
        val response = myService.uploadBlog(head = head, img, desc = desc, author = author)

        response.enqueue(object : Callback<BlogPostResponse>{
            override fun onResponse(
                call: Call<BlogPostResponse>,
                response: Response<BlogPostResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    Thread {
                        onSuccess(response.body()!!)
                    }.start()
                } else {
                    onError(response.message())
                }
            }

            override fun onFailure(call: Call<BlogPostResponse>, t: Throwable) {

                onError(t.localizedMessage ?: "Something went wrong")
            }

        })
    }


}