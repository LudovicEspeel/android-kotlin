package com.example.jetpacktest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

// example 1: MainViewModel without parameters
class MainViewModel1() : ViewModel() {
    var counter = 0
}

// example 2: MainViewModel with parameters
class MainViewModel2(countReserved: Int) : ViewModel() {
    var counter = countReserved
}

// example 3: MainViewModel with parameters and MutableLiveData
class MainViewModel3(countReserved: Int) : ViewModel() {

    val counter = MutableLiveData<Int>()
    // problem: counter is mutable and accessible from other classes

    init {
        counter.value = countReserved // setValue is called
    }

    fun plusOne() {
        val count = counter.value ?: 0 // getValue is called
        counter.value = count + 1 // setValue is called
    }

    fun clear() {
        counter.value = 0 // setValue is called
    }
}

// example 4: MainViewModel with parameters and MutableLiveData (counter refactored)
class MainViewModel4(countReserved: Int) : ViewModel() {

    private val _counter = MutableLiveData<Int>()
    private val _userLiveData = MutableLiveData<User>()

    val counter: LiveData<Int>
        get() = _counter

    val userName: LiveData<String> = Transformations.map(_userLiveData)
    { user ->
        "${user.firstName} ${user.lastName}" // only expose the fields firstName and lastName
    }

    init {
        _counter.value = countReserved
        _userLiveData.value = User("John", "Doe", 18)
    }

    fun plusOne() {
        val count = _counter.value ?: 0
        _counter.value = count + 1
    }

    fun clear() {
        _counter.value = 0
    }

    fun getUser() {
        _userLiveData.value = User("Jane", "Doe", 19)
    }
}