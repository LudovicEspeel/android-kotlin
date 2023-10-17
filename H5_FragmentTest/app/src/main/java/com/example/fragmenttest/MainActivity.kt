package com.example.fragmenttest

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.fragmenttest.databinding.LeftFragmentBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: LeftFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = supportFragmentManager.findFragmentById(R.id.leftFrag) as LeftFragment

        // zonder binding:
        /*val button = fragment.view?.findViewById<Button>(R.id.button)
        button?.setOnClickListener {
            replaceFragment(AnotherRightFragment())
        }*/

        // met binding:
        binding = LeftFragmentBinding.bind(fragment.requireView())
        binding.button.setOnClickListener {
            replaceFragment(AnotherRightFragment())
        }

        // in commentaar zetten voor het voorbeeld van dynamisch laden layout volgens resolutie:
        replaceFragment(RightFragment())
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.rightLayout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}