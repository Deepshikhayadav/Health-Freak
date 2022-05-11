package com.deepshikhayadav.geetacollege.ui.pedometer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PedometerViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is pedometer Fragment"
    }
    val text: LiveData<String> = _text
}