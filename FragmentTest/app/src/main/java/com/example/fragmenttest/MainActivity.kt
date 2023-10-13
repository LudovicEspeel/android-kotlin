package com.example.fragmenttest

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.fragmenttest.databinding.LeftFragmentBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: LeftFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


       /* (fragmentManager.findFragmentById().view!!.findViewById<View>(R.id.one_nextpager) as Button).setOnClickListener(
            object : OnClickListener() {
                fun onClick(v: View?) {}
            })
*/
        val view = layoutInflater.inflate(R.layout.left_fragment, null)


        binding = LeftFragmentBinding.bind(view)
        binding.button.setOnClickListener {
            replaceFragment(AnotherRightFragment())
        }
        /*with(binding)
        {
            button.setOnClickListener {
                replaceFragment(AnotherRightFragment())
            }
        }*/
        replaceFragment(RightFragment())
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.rightLayout, fragment)
        transaction.commit()
    }
}