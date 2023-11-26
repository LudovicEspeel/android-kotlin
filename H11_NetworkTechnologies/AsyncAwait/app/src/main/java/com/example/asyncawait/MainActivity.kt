package com.example.asyncawait

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.asyncawait.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                val result = async {
                    delay(1000)
                    "Hello World delayed 1s!"
                }

                val message = result.await()
                Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()

                val secondResult = async {
                    delay(1000)
                    "Hello World delayed 2s!"
                }

                val secondMessage = secondResult.await()
                Toast.makeText(applicationContext, secondMessage, Toast.LENGTH_SHORT).show()
            }
            Toast.makeText(applicationContext, "Hello World, delayed 0s!", Toast.LENGTH_SHORT).show()
        }
    }
}