package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.calculator.R
import kotlin.math.sqrt
import android.util.Log

//referenced this tutorial for the backend logic:
//https://www.youtube.com/watch?v=2hSHgungOKI&ab_channel=CodeWithCal
class MainActivity : AppCompatActivity() {

    private var addOperation = false
    private var addDecimal = true

    private lateinit var inputs: TextView
    private lateinit var results: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        inputs = findViewById<TextView>(R.id.inputs)
        results = findViewById<TextView>(R.id.results)

    }

    fun equalsClick(view: View) {
        results.text = calculateResults()
        inputs.text = ""
    }


    private fun calculateResults(): String
    {
        val digitsOperators = operatorList()
        if(digitsOperators.isEmpty()) return ""

        val mulDivMod = mulDivModCalculate(digitsOperators)
        if(mulDivMod.isEmpty()) return ""

        val result = addSubCalculate(mulDivMod)
        return result.toString()
    }

    private fun addSubCalculate(passedList: MutableList<Any>): Float
    {
        var result = passedList[0] as Float

        for(i in passedList.indices)
        {
            if(passedList[i] is Char && i != passedList.lastIndex)
            {
                val operator = passedList[i]
                val nextDigit = passedList[i + 1] as Float
                if (operator == '+')
                    result += nextDigit
                if (operator == '-')
                    result -= nextDigit
            }
        }

        return result
    }

    private fun mulDivModCalculate(passedList: MutableList<Any>): MutableList<Any>
    {
        var list = passedList
        while (list.contains('x') || list.contains('/') || list.contains('%'))
        {
            list = mulDivModFunc(list)
        }
        return list
    }

    private fun mulDivModFunc(passedList: MutableList<Any>): MutableList<Any>
    {
        val newList = mutableListOf<Any>()
        var restartIndex = passedList.size

        for(i in passedList.indices) {
            if(passedList[i] is Char && i != passedList.lastIndex && i < restartIndex) {
                val operator = passedList[i]
                val prev = passedList[i-1] as Float
                val next = passedList[i + 1] as Float
                when(operator) {
                    'x' -> {
                        newList.add(prev * next)
                        restartIndex = i + 1
                    }
                    '/' -> {
                        newList.add(prev/ next)
                        restartIndex = i + 1
                    }

                    '%' -> {
                        newList.add(prev % next)
                        restartIndex = i + 1
                    }
                    else -> {
                        newList.add(prev)
                        newList.add(operator)
                    }
                }
            }

            if(i > restartIndex)
                newList.add(passedList[i])
        }

        return newList
    }

    private fun operatorList(): MutableList<Any> {
            val list = mutableListOf<Any>()
            var digit = ""
            for(char in inputs.text) {
                if(char.isDigit() || char == '.') {
                    digit += char
                }
                else {
                    list.add(digit.toFloat())
                    digit = ""
                }
            }

            if(digit != "") {
                list.add(digit.toFloat())
            }
            return list
        }


    fun operateClick(view: View) {
        if(addOperation && view is Button) {
            inputs.append(view.text)
            addOperation = false
            addDecimal = true
        }
    }

    //button functions
    fun numberClick(view: View) {
        if(view is Button) {
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

    fun deleteClick(view: View) {
        if(inputs.length() > 0) {
            inputs.text = inputs.text.subSequence(0, inputs.length() -1)
        }
    }

    fun clearClick(view: View) {
        inputs.text = ""
    }
}