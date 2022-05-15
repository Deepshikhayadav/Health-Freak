package com.deepshikhayadav.geetacollege.retrofit.remote

import com.deepshikhayadav.geetacollege.local_db.dao.MyDao
import com.deepshikhayadav.geetacollege.local_db.entity.BlogResponse
import com.deepshikhayadav.geetacollege.local_db.entity.YogaResponse
import com.deepshikhayadav.geetacollege.retrofit.MyService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

}