package com.deepshikhayadav.geetacollege.ui.yoga

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class YogaViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is yoha Fragment"
    }
    val text: LiveData<String> = _text
}