package com.example.jetpacktest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

// from example 2:
class MainViewModelFactory2(private val countReserved: Int) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel2(countReserved) as T
    }
}

class MainViewModelFactory3(private val countReserved: Int) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel3(countReserved) as T
    }
}

class MainViewModelFactory4(private val countReserved: Int) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel4(countReserved) as T
    }
}