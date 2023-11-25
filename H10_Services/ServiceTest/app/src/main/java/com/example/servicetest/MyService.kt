package com.example.servicetest

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class MyService : Service() {

    private val mBinder = DownloadBinder()

    override fun onCreate() {
        super.onCreate()
        Log.d("MyService", "onCreate executed")
        // boek p. 451
        // foreground service:
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("my_service", "Foreground Service Notification", NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }
        val intent = Intent(this, MainActivity::class.java)
        val pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val notification = NotificationCompat.Builder(this, "my_service")
            .setContentTitle("This is content title")
            .setContentText("This is content text")
            .setSmallIcon(R.drawable.sync_alt_fill0_wght400_grad0_opsz48)
            .setLargeIcon(
                BitmapFactory.decodeResource(resources,
                R.drawable.vives_logo))
            .setContentIntent(pi)
            .build()
        startForeground(1, notification)
        //
    }

    // boek p. 441-445
    // probleem: hiermee kan de status van de Service niet opgevraagd worden:
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d("MyService", "onStartCommand executed")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyService", "onDestroy executed")
    }
    //

    override fun onBind(intent: Intent): IBinder {
        return mBinder
    }


    // boek p. 445-450
    // oplossing bovenstaand probleem: hiermee kan een Activity weten wat de status is van de Service
    class DownloadBinder : Binder() {
        fun startDownload() {
            Log.d("MyService", "startDownload executed")
        }
        fun getProgress(): Int {
            Log.d("MyService", "getProgress executed")
            return 0
        }
    }
    //
}