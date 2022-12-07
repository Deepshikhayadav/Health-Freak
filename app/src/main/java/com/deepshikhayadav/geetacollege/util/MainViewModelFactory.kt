package com.deepshikhayadav.geetacollege.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.deepshikhayadav.geetacollege.retrofit.remote.MyRepository
import com.deepshikhayadav.geetacollege.ui.blogs.BlogsViewModel
import com.deepshikhayadav.geetacollege.ui.yoga.YogaViewModel

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val networkHelper: NetworkHelper,
    private val myRepository: MyRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BlogsViewModel(networkHelper, myRepository) as T
    }
}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory2(
    private val networkHelper: NetworkHelper,
    private val myRepository: MyRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return YogaViewModel(networkHelper, myRepository) as T
    }
}
