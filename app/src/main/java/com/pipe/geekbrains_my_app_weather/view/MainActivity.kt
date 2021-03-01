package com.pipe.geekbrains_my_app_weather.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pipe.geekbrains_my_app_weather.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}