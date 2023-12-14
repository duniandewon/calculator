package com.example.calcultor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.calcultor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val calcViewModel = ViewModelProvider(this)[CalculatorViewModel::class.java]

        val buttons = listOf(
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

                if (buttonText in "0123456789.") calcViewModel.setOperand(buttonText)

                if (buttonText in "X/+-") calcViewModel.setOperator(buttonText)

                if (buttonText.lowercase() == "reset") calcViewModel.resetCalculator()

                if (buttonText.lowercase() == "del") calcViewModel.onDelete()

                if (buttonText == "=") calcViewModel.onCalculate()
            }
        }

        calcViewModel.resultLiveData.observe(this) {
            binding.editTextNumberSigned.text = it
        }
    }
}