package com.example.uilayouttest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    // dit project hoef je niet per sé uit te voeren in de emulator,
    // bekijk de layout-bestanden in de design weergave
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_linear1)
    }
}