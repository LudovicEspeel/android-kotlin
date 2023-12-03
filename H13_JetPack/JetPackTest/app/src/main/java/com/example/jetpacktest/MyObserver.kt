package com.example.jetpacktest

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class MyObserver : DefaultLifecycleObserver {

    override fun onStart(owner: LifecycleOwner) {
        Log.d("MyObserver", "activityStart")
    }

    override fun onStop(owner: LifecycleOwner) {
        Log.d("MyObserver", "activityStop")
    }
}