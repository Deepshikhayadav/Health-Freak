package com.deepshikhayadav.geetacollege.ui.blogs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BlogsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is blog Fragment"
    }
    val text: LiveData<String> = _text
}