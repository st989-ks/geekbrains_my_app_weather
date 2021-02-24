package com.pipe.geekbrains_my_app_weather

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        var first: MyClass = MyClass("", 1)
        val button: Button = findViewById(R.id.test_button)
        var etText = findViewById<EditText>(R.id.text_enter)
        var tvText = findViewById<TextView>(R.id.text_view)

        button.setOnClickListener {
            val second: MyClass = first.copy(text = etText.text.toString())
            tvText.text = etText.text
            Toast.makeText(this@MainActivity, "${second.text}, ${second.number}", Toast.LENGTH_LONG).show()
            cycle()
        }
    }
    
    fun cycle (){
        val list = listOf(1, 2, 1, 4, 22)
        list.forEach {
            Log.d(TAG_FIRST, it.toString())
        }
        for (i in 1..50 step 2) {
            Log.d(TAG_SECOND, i.toString())
        }
        for (i in 20 downTo 1 step 3) {
            Log.d(TAG_THIRD, i.toString())
        }
    }
}