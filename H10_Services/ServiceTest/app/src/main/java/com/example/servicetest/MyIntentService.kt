package com.example.servicetest

import android.app.IntentService
import android.content.Intent
import android.util.Log

// boek p. 454-456
class MyIntentService : IntentService("MyIntentService") {
    override fun onHandleIntent(intent: Intent?) {
        // log the id of the current thread
        Log.d("MyIntentService", "Thread id is ${Thread.currentThread().name}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyIntentService", "onDestroy executed")
    }
}
//