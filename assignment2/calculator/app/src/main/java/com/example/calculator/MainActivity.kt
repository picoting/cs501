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
    }

    /*
    private fun calculate(): String {

        val operatorList = operatorList()
        if(operatorList.size == 0) {
            return ""
        }

        var result = operatorList[0] as? Float ?: return ""
        var currentOperator: Char? = null

        for (i in 1 until operatorList.size) {
            val element = operatorList[i]
            when {
                element is Char -> {
                    currentOperator = element
                }
                element is Float && currentOperator != null -> {

                    //result = calculateOperation(result, element, currentOperator)

                    result = 8943059.00.toFloat()
                    currentOperator = null
                }
            }
        }


        // Return the result as a String
        return result.toString()
    }



        private fun calculateOperation(result: Float, number: Float, operator: Char): Float {
            return when (operator) {
                '+' -> result + number
                '-' -> result - number
                '*' -> result * number
                '/' -> if (number != 0f) result / number else Float.NaN // Check for division by zero
                '√' -> sqrt(number)
                else -> result // Unknown operator, just return the result so far
            }
        }

     */

    private fun calculateResults(): String
    {
        val digitsOperators = digitsOperators()
        if(digitsOperators.isEmpty()) return ""

        val timesDivision = timesDivisionCalculate(digitsOperators)
        if(timesDivision.isEmpty()) return ""

        val result = addSubtractCalculate(timesDivision)
        return result.toString()
    }

    private fun addSubtractCalculate(passedList: MutableList<Any>): Float
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

    private fun timesDivisionCalculate(passedList: MutableList<Any>): MutableList<Any>
    {
        var list = passedList
        while (list.contains('x') || list.contains('/'))
        {
            list = calcTimesDiv(list)
        }
        return list
    }

    private fun calcTimesDiv(passedList: MutableList<Any>): MutableList<Any>
    {
        val newList = mutableListOf<Any>()
        var restartIndex = passedList.size

        for(i in passedList.indices)
        {
            if(passedList[i] is Char && i != passedList.lastIndex && i < restartIndex)
            {
                val operator = passedList[i]
                val prevDigit = passedList[i - 1] as Float
                val nextDigit = passedList[i + 1] as Float
                when(operator)
                {
                    'x' ->
                    {
                        newList.add(prevDigit * nextDigit)
                        restartIndex = i + 1
                    }
                    '/' ->
                    {
                        newList.add(prevDigit / nextDigit)
                        restartIndex = i + 1
                    }
                    else ->
                    {
                        newList.add(prevDigit)
                        newList.add(operator)
                    }
                }
            }

            if(i > restartIndex)
                newList.add(passedList[i])
        }

        return newList
    }

    private fun digitsOperators(): MutableList<Any>
    {
        val list = mutableListOf<Any>()
        var currentDigit = ""
        for(character in inputs.text)
        {
            if(character.isDigit() || character == '.')
                currentDigit += character
            else
            {
                list.add(currentDigit.toFloat())
                currentDigit = ""
                list.add(character)
            }
        }

        if(currentDigit != "")
            list.add(currentDigit.toFloat())

        return list
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