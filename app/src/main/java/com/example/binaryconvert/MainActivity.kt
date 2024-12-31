package com.example.binaryconvert

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.InputFilter
import android.text.method.DigitsKeyListener
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import kotlin.math.pow

@Suppress("NAME_SHADOWING", "NAME_SHADOWING")
class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n", "UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val resText = findViewById<TextView>(R.id.result1)
        val button = findViewById<Button>(R.id.button1)
        val switch = findViewById<Switch>(R.id.simpleSwitch)
        lateinit var firstNumberText: TextInputEditText
        lateinit var secondNumberText: TextInputEditText
        switch.isChecked = false
        if (!switch.isChecked) {
            firstNumberText = findViewById(R.id.edit_text1)
            secondNumberText = findViewById(R.id.edit_text2)
            firstNumberText.keyListener = DigitsKeyListener.getInstance("01")
            secondNumberText.keyListener = DigitsKeyListener.getInstance("01")
        }
        switch.setOnClickListener {
            if (switch.isChecked) {
                firstNumberText = findViewById(R.id.edit_text1)
                secondNumberText = findViewById(R.id.edit_text2)
                firstNumberText.keyListener = DigitsKeyListener.getInstance("0123456789")
                secondNumberText.keyListener = DigitsKeyListener.getInstance("0123456789")
            } else {
                firstNumberText = findViewById(R.id.edit_text1)
                secondNumberText = findViewById(R.id.edit_text2)
                firstNumberText.keyListener = DigitsKeyListener.getInstance("01")
                secondNumberText.keyListener = DigitsKeyListener.getInstance("01")
            }
        }

        button.setOnClickListener {

            val firstNumberTextGLOBAL = firstNumberText.text.toString()

            val secondNumberTextGLOBAL = secondNumberText.text.toString()

            switch.text = if (switch.isChecked) "Decimal" else "Binary"

            if (switch.isChecked) {
                val firstNumber = firstNumberTextGLOBAL.toIntOrNull()
                val secondNumber = secondNumberTextGLOBAL.toIntOrNull()

                if (firstNumber != null && secondNumber != null) {
                    val resultDec = firstNumber + secondNumber
                    resText.text = "Bin: ${decimalToBinary(firstNumber.toLong())} + ${
                        decimalToBinary(secondNumber.toLong())
                    } = ${
                        sum(
                            decimalToBinary(firstNumber.toLong()),
                            decimalToBinary(secondNumber.toLong())
                        )
                    }\n\n" +
                            "Dec: $firstNumber + $secondNumber = $resultDec"
                } else {
                    Toast.makeText(this, "Invalid input numbers.", Toast.LENGTH_SHORT).show()
                }
            } else {
                if (firstNumberTextGLOBAL.length == 7 && secondNumberTextGLOBAL.length == 7) {
                    val firstNumber = firstNumberTextGLOBAL.toLong()
                    val secondNumber = secondNumberTextGLOBAL.toLong()
                    val result = binaryToDecimal(firstNumber.toString()).toLong() + binaryToDecimal(
                        secondNumber.toString()
                    ).toLong()
                    resText.text = "Bin: $firstNumberTextGLOBAL + $secondNumberTextGLOBAL = ${
                        sum(
                            firstNumberTextGLOBAL,
                            secondNumberTextGLOBAL
                        )
                    }\n\n" +
                            "Dec: ${binaryToDecimal(firstNumber.toString())} + ${
                                binaryToDecimal(
                                    secondNumber.toString()
                                )
                            } = $result"
                } else {
                    Toast.makeText(
                        this,
                        "Both numbers should have exactly 7 digits.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        switch.setOnCheckedChangeListener { _, isChecked ->
            switch.text = if (isChecked) "Decimal" else "Binary"
        }
    }

    private fun binaryToDecimal(binary: String): Int {
        var decimal = 0
        var power = 0

        for (i in binary.length - 1 downTo 0) {
            val digit = binary[i] - '0'
            decimal += digit * 2.0.pow(power.toDouble()).toInt()
            power++
        }

        return decimal
    }

    private fun decimalToBinary(n: Long): String {
        if (n.toInt() == 0) {
            return "0"
        }
        var binary = ""
        var num = n
        while (num > 0) {
            binary = (num % 2).toString() + binary
            num /= 2
        }
        while (binary.length < 7) {
            binary = "0$binary"
        }
        return binary
    }

    private fun sum(firstNumber: String, secondNumber: String): String {
        var transfer = 0
        var result = ""

        val firstNumber1 = firstNumber
        val secondNumber1 = secondNumber

        for (i in 6 downTo 0) {
            val digitSum = Character.getNumericValue(firstNumber1[i]) +
                    Character.getNumericValue(secondNumber1[i]) +
                    transfer
            val sumDigit = digitSum % 2
            transfer = digitSum / 2
            result = "$sumDigit$result"
        }

        result = "$transfer$result"
        return result
    }
}