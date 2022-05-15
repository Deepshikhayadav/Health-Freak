package com.deepshikhayadav.geetacollege.ui.yoga

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deepshikhayadav.geetacollege.local_db.entity.YogaResponse
import com.deepshikhayadav.geetacollege.retrofit.remote.MyRepository
import com.deepshikhayadav.geetacollege.util.NetworkHelper

class YogaViewModel(private val networkHelper: NetworkHelper,
                    private val myRepository: MyRepository
) : ViewModel() {

    companion object {
        private const val API_KEY = "7bc0651fe0ea5973822df3bd27e779d9"
        private const val SOMETHING_WENT_WRONG = "Something went wrong"
    }

    private val _myResponse = MutableLiveData<YogaResponse>()
    val myResponse: LiveData<YogaResponse>
        get() = _myResponse

    private val _errorResponse = MutableLiveData<String>()
    val errorResponse: LiveData<String>
        get() = _errorResponse

    fun onCreate() {
        if (networkHelper.isNetworkConnected()) {
            myRepository.fetchYogas(/*API_KEY,*/ { MyResponse ->
                _myResponse.postValue(MyResponse)
            }, { error ->
                _errorResponse.postValue(error)
            })
        } else {
            myRepository.getYogasLocal { myResponse ->
                if (myResponse != null && myResponse.data.isNotEmpty()) {
                    _myResponse.postValue(myResponse)
                } else {
                    _errorResponse.postValue(SOMETHING_WENT_WRONG)
                }
            }
        }
    }
}