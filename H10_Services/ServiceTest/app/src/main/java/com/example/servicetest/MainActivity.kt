package com.example.servicetest

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.example.servicetest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var downloadBinder: MyService.DownloadBinder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // boek p. 441-445
        // probleem: hiermee kan de status van de Service niet opgevraagd worden:
        binding.startServiceBtn.setOnClickListener {
            val intent = Intent(this, MyService::class.java)
            startService(intent) // start Service
        }

        binding.stopServiceBtn.setOnClickListener {
            val intent = Intent(this, MyService::class.java)
            stopService(intent) // stop Service
        }
        //

        // boek p. 445-447
        // oplossing:
        binding.bindServiceBtn.setOnClickListener {
            val intent = Intent(this, MyService::class.java)
            bindService(intent, connection, Context.BIND_AUTO_CREATE) // bind Service
        }
        binding.unbindServiceBtn.setOnClickListener {
            unbindService(connection) // unbind Service
        }
        //

        // boek p. 454-456
        binding.startIntentServiceBtn.setOnClickListener {
            // log main thread id
            Log.d("MainActivity", "Thread id is ${Thread.currentThread().name}")

            val intent = Intent(this, MyIntentService::class.java)
            startService(intent)

            // nieuwere manier, werken met JobSchedulel en JobService:
            /*val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            val jobInfo = JobInfo.Builder(1, ComponentName(this, MyJobService::class.java))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setRequiresCharging(true)
                .setOverrideDeadline(0)
                .build()
            jobScheduler.schedule(jobInfo)*/
        }
        //
    }

    // boek p. 445-447
    // object = anonieme klasse
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            downloadBinder = service as MyService.DownloadBinder
            downloadBinder.startDownload()
            downloadBinder.getProgress()
        }
        override fun onServiceDisconnected(name: ComponentName) {
        }
    }
    //
}