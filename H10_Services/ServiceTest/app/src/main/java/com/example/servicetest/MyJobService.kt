package com.example.servicetest

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log

// nieuwere manier i.p.v. IntentService (begint deprecated te worden)
class MyJobService : JobService(){
    override fun onStartJob(params: JobParameters?): Boolean {
        // Background task logic goes here
        Log.d("MyJobService", "Thread id is ${Thread.currentThread().name}")

        // Remember to call jobFinished() when the job is done
        jobFinished(params, false)

        return false // Return true if there is more work to be done, false otherwise
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        // Called if the job is prematurely stopped
        Log.d("MyJobService", "onStopJob executed")
        return false // Return true to reschedule the job, false otherwise
    }
}
//