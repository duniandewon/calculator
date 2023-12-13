package com.example.calcultor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.calcultor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var result: TextView

    private var operand: Double = 0.0
    private var isOperating: Boolean = false
    private var operator: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        result = binding.editTextNumberSigned

        result.text = "0"

        val buttons = listOf<Button>(
            binding.btn0,
            binding.btn1,
            binding.btn2,
            binding.btn3,
            binding.btn4,
            binding.btn5,
            binding.btn6,
            binding.btn7,
            binding.btn8,
            binding.btn9,
            binding.btnDecimal,
            binding.btnDel,
            binding.btnPlus,
            binding.btnMinus,
            binding.btnX,
            binding.btnDivide,
            binding.btnReset,
            binding.btnEqual
        )

        buttons.forEach { button ->
            button.setOnClickListener {
                val buttonText = button.text.toString()

                if (buttonText in "0123456789.") handleClickNumber(buttonText)

                if (buttonText in "X/+-") handleClickOperator(buttonText)

                if (buttonText.lowercase() == "reset") handleClickReset()

                if (buttonText.lowercase() == "del") handleClickDel()

                if (buttonText == "=") handleClickEqual()
            }
        }
    }

    private fun calculate(): Double {
        var res = 0.0

        when (operator) {
            "+" -> {
                res = operand!! + result.text.toString().toDouble()
            }

            "-" -> {
                res = operand!! - result.text.toString().toDouble()
            }

            "X" -> {
                res = operand!! * result.text.toString().toDouble()
            }

            "/" -> {
                res = operand!! / result.text.toString().toDouble()
            }
        }

        return res
    }

    private fun handleClickEqual() {
        result.text = calculate().toString()
        operand = 0.0
        operator = ""
        isOperating = false
    }

    private fun handleClickOperator(op: String) {
        if (!isOperating && operand > 0) {
            val res = calculate()
            result.text = res.toString()
            operand = res
        } else {
            operand = result.text.toString().toDouble()
        }

        operator = op
        isOperating = true
    }

    private fun handleClickReset() {
        result.text = "0"
        operand = 0.0
        isOperating = false
        operator = ""
    }

    private fun handleClickDel() {
        if (result.text.length == 1) return result.setText("0")

        return result.setText(result.text.slice(0..<result.text.length - 1))
    }

    private fun handleClickNumber(number: String) {
        if (operand > 0 && isOperating) {
            result.text = ""
            isOperating = false
        }

        if (number == "." && result.text.indexOf(".") > -1) return


        if (result.text.toString() == "0" && number != ".") {
            return result.setText(number)
        }

        return result.append(number)
    }
}