package com.deepshikhayadav.geetacollege.ui.blogs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deepshikhayadav.geetacollege.local_db.entity.BlogPostResponse
import com.deepshikhayadav.geetacollege.local_db.entity.BlogResponse
import com.deepshikhayadav.geetacollege.retrofit.remote.MyRepository
import com.deepshikhayadav.geetacollege.util.NetworkHelper
import java.io.File

class BlogsViewModel
    (private val networkHelper: NetworkHelper,
     private val myRepository: MyRepository
) : ViewModel() {

    companion object {
       // private const val API_KEY = "7bc0651fe0ea5973822df3bd27e779d9"
        private const val SOMETHING_WENT_WRONG = "Something went wrong"
    }

    private val _myResponse = MutableLiveData<BlogResponse>()
    val myResponse: LiveData<BlogResponse>
    get() = _myResponse

    private val _errorResponse = MutableLiveData<String>()
    val errorResponse: LiveData<String>
    get() = _errorResponse

    private val _postResponse = MutableLiveData<BlogPostResponse>()
    val postResponse: LiveData<BlogPostResponse>
        get() = _postResponse

    fun onCreate() {
        if (networkHelper.isNetworkConnected()) {
            myRepository.fetchBlogs(/*API_KEY,*/ { MyResponse ->
                _myResponse.postValue(MyResponse)
            }, { error ->
                _errorResponse.postValue(error)
            })
        } else {
            myRepository.getBlogsLocal { myResponse ->
                if (myResponse != null && myResponse.data.isNotEmpty()) {
                    _myResponse.postValue(myResponse)
                } else {
                    _errorResponse.postValue(SOMETHING_WENT_WRONG)
                }
            }
        }

    }

    fun blogUpload(head: String, desc: String, author: String, file: File) {
        if (networkHelper.isNetworkConnected()) {
            myRepository.uploadBlog(head,desc,author,file, { myResponse ->
                _postResponse.postValue(myResponse)
            }, { error ->
                _errorResponse.postValue(error)
            })
        }
    }
}