package com.example.arithmetic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    private lateinit var button: Button
    private lateinit var input1: EditText
    private lateinit var input2: EditText
    private var outputVal by Delegates.notNull<Double>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button)
        input1 = findViewById(R.id.input1)
        input2 = findViewById(R.id.input2)
        val output: TextView = findViewById<TextView>(R.id.output) //initialize output textview

        val operations = resources.getStringArray(R.array.operators) //list of spinner options
        val spinner = findViewById<Spinner>(R.id.spinner) //access spinner

        //i dont rly get what the adapter does???
        //referenced this geeksforgeeks page: https://www.geeksforgeeks.org/spinner-in-kotlin/
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, operations
            )
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val num1 = input1.text.toString().toDoubleOrNull() ?: 0.0
                val num2 = input2.text.toString().toDoubleOrNull() ?: 0.0

                outputVal = when (parent.getItemAtPosition(position).toString()) {
                    "add" -> num1 + num2
                    "subtract" -> num1 - num2
                    "multiply" -> num1 * num2
                    "divide" -> if (num2 != 0.0) num1 / num2 else Double.NaN //division by zero
                    "modulo" -> if (num2 != 0.0) num1 % num2 else Double.NaN //modulo by zero
                    else -> Double.NaN
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // i think this can just be blank?
            }
        }

        button.setOnClickListener {
            //string = editText.text.toString()
            output.text = outputVal.toString() //change the output text upon button click
        }
    }
}