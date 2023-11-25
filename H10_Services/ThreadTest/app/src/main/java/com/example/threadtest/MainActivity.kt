package com.example.threadtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import com.example.threadtest.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val updateText = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.changeTextBtn.setOnClickListener {
            thread {
                //binding.textView.text = "Nice to meet you"
                // zal crashen omdat de UI threadtest niet mag aangepast worden vanuit een andere threadtest

                // oplossing:
                val msg = Message()
                msg.what = updateText
                handler.sendMessage(msg) // send the Message object
            }
        }
    }

    val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            // Updating UI here is fine
            when (msg.what) {
                updateText -> binding.textView.text = "Nice to meet you"
            }
        }
    }
}