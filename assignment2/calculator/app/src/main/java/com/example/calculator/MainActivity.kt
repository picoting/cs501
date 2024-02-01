package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.calculator.R

//referenced this tutorial for the backend logic:
//https://www.youtube.com/watch?v=2hSHgungOKI&ab_channel=CodeWithCal
class MainActivity : AppCompatActivity() {

    private var addOperation = false
    private var addDecimal = true

    private lateinit var inputs: TextView
    private lateinit var results: TextView

    val stringval = "helloooo"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        inputs = findViewById<TextView>(R.id.inputs)
        results = findViewById<TextView>(R.id.results)

    }

    fun equalsClick(view: View) {}
    fun operate(view: View) {}
    fun numberClick(view: View) {
        if(view is Button) {
            inputs.text = stringval

            if(view.text == ".") {
                if(addDecimal) {
                    inputs.append(view.text)
                }
                addDecimal = false
            }
            else {
                inputs.append(view.text)
            }
            addOperation = true
        }
    }
}