package com.example.jetpacktest

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProvider
import com.example.jetpacktest.databinding.ActivityMainBinding

// to run this example, comment out one example block at a time:
class MainActivity : AppCompatActivity() {

    lateinit var viewModel1: MainViewModel1
    lateinit var viewModel2: MainViewModel2
    lateinit var viewModel3: MainViewModel3
    lateinit var viewModel4: MainViewModel4

    lateinit var binding: ActivityMainBinding

    lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        // example 1: ViewModel without parameters
//        //viewModel1 = ViewModelProviders.of(this).get(MainViewModel1::class.java) //deprecated
//        viewModel1 = ViewModelProvider(this).get(MainViewModel1::class.java)
//
//        binding.plusOneButton.setOnClickListener {
//            viewModel1.counter++
//            refreshCounter1()
//        }
//
//        binding.clearButton.setOnClickListener {
//            viewModel1.counter = 0
//            refreshCounter1()
//        }
//        refreshCounter1()



//        // example 2: ViewModel with parameters
//        // get the value of countReserved from SharedPreferences:
//        sp = getPreferences(MODE_PRIVATE)
//        val countReserved = sp.getInt("COUNTER", 0)
//        // to pass values to the ViewModel, we need to use ViewModelProvider.Factory:
//        viewModel2 = ViewModelProvider(this, MainViewModelFactory2(countReserved)).get(MainViewModel2::class.java)
//
//        binding.plusOneButton.setOnClickListener {
//            viewModel2.counter++
//            refreshCounter2()
//        }
//
//        binding.clearButton.setOnClickListener {
//            viewModel2.counter = 0
//            refreshCounter2()
//        }
//        refreshCounter2()


//        // example 3: using observer and LiveData:
//        sp = getPreferences(MODE_PRIVATE)
//        val countReserved = sp.getInt("COUNTER", 0)
//        viewModel3 = ViewModelProvider(this, MainViewModelFactory3(countReserved)).get(MainViewModel3::class.java)
//        lifecycle.addObserver(MyObserver())
//
//        binding.plusOneButton.setOnClickListener {
//            viewModel3.plusOne()
//        }
//
//        binding.clearButton.setOnClickListener {
//            viewModel3.clear()
//        }
//
//        //viewModel.counter.observe(this, Observer { count ->
//        viewModel3.counter.observe(this) { count ->
//            binding.infoTextView.text = count.toString()
//        }


        // example 4: using observer and LiveData (refactored):
        sp = getPreferences(MODE_PRIVATE)
        val countReserved = sp.getInt("COUNTER", 0)
        viewModel4 = ViewModelProvider(this, MainViewModelFactory4(countReserved)).get(MainViewModel4::class.java)
        lifecycle.addObserver(MyObserver())

        binding.plusOneButton.setOnClickListener {
            viewModel4.plusOne()
        }

        binding.clearButton.setOnClickListener {
            viewModel4.clear()
        }

        binding.getUserButton.setOnClickListener {
            viewModel4.getUser()
        }

        viewModel4.counter.observe(this) { count ->
            binding.infoTextView.text = count.toString()
        }

        viewModel4.userName.observe(this){ user ->
            binding.infoTextView.text=user
        }

        //TODO: nice demo at the end, change the theme of the app in the manifest file from BasicViewModel to:
        //android:theme="@style/Theme.JetpackTest"
    }

    override fun onPause() {
        super.onPause()

//        // example 2:
//        sp.edit {
//            putInt("COUNTER", viewModel2.counter)
//        }

//        // example 3:
//        sp.edit {
//            putInt("COUNTER", viewModel3.counter.value ?: 0)
//        }

        // example 4:
        sp.edit {
            putInt("COUNTER", viewModel4.counter.value ?: 0)
        }
    }

    private fun refreshCounter1() {
        binding.infoTextView.text = viewModel1.counter.toString()
    }

    private fun refreshCounter2() {
        binding.infoTextView.text = viewModel2.counter.toString()
    }
}