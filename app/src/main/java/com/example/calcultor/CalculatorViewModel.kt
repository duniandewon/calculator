package com.example.calcultor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {
    private var result = "0"

    private var operand = 0.0

    private var isOperating = false

    private var operator = ""

    private var resultMutableLiveData = MutableLiveData<String>()
    var resultLiveData: LiveData<String> = resultMutableLiveData

    init {
        resultMutableLiveData.value = "0"
    }

    private fun calculate(): Double {
        return when (operator) {
            "+" -> operand + result.toDouble()
            "-" -> operand - result.toDouble()
            "X" -> operand * result.toDouble()
            "/" -> operand / result.toDouble()
            else -> 0.0
        }
    }

    fun onCalculate() {
        result = calculate().toString()
        operand = 0.0
        isOperating = false
        operator = ""

        resultMutableLiveData.value = result
    }

    fun setOperator(op: String) {
        if (!isOperating && operand > 0) {
            val res = calculate()
            result = res.toString()
            operand = res
        } else {
            operand = result.toDouble()
        }

        resultMutableLiveData.value = result

        operator = op
        isOperating = true
    }

    fun setOperand(number: String) {
        if (number == "." && result.contains(".")) return

        if (operand > 0 && isOperating) {
            result = ""
            isOperating = false
        }

        if (result == "0" && number != ".") result = number
        else result += number

        resultMutableLiveData.value = result
    }

    fun onDelete() {
        result = if (result.length == 1) "0"
        else (result.slice(0..<result.length - 1))

        resultMutableLiveData.value = result
    }

    fun resetCalculator() {
        result = "0"
        operand = 0.0
        isOperating = false
        operator = ""

        resultMutableLiveData.value = result
    }
}