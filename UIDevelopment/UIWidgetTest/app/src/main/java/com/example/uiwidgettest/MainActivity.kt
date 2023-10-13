package com.example.uiwidgettest

import android.app.ProgressDialog.show
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.uiwidgettest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //binding.button.setOnClickListener(this)

        with(binding)
        {
            button.setOnClickListener {
                val inputText = editText.text.toString()
                Toast.makeText(this@MainActivity, inputText, Toast.LENGTH_SHORT).show()

                imageView.setImageResource(R.drawable.img_2)

                //if (progressBar.visibility == View.VISIBLE) {
                //    progressBar.visibility = View.GONE
                //} else {
                //    progressBar.visibility = View.VISIBLE
                progressBar.progress = progressBar.progress + 10
                //}

                AlertDialog.Builder(this@MainActivity).apply {
                    setTitle("This is Dialog")
                    setMessage("Something important.")
                    setCancelable(false) // dialoogvenster kan niet weggeklikt worden
                    setPositiveButton("OK") { dialog, which -> }
                    setNegativeButton("Cancel") { dialog, which ->
                    }
                    show()
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button -> {
                val inputText = binding.editText.text.toString()
                Toast.makeText(this, inputText, Toast.LENGTH_SHORT).show()
            }
        }
    }
}