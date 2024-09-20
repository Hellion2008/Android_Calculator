package com.example.android_calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var firstOperandET: EditText
    private lateinit var secondOperandET: EditText

    private lateinit var buttonSumBTN: Button
    private lateinit var buttonDifBTN: Button
    private lateinit var buttonMultBTN: Button
    private lateinit var buttonDivBTN: Button

    private lateinit var resultTextView: TextView

    private lateinit var buttonResetBTN: Button
    private lateinit var buttonExitBTN: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        firstOperandET = findViewById(R.id.firstOperandET)
        secondOperandET = findViewById(R.id.secondOperandET)

        buttonSumBTN = findViewById(R.id.buttonSumBTN)
        buttonDifBTN = findViewById(R.id.buttonDifBTN)
        buttonMultBTN = findViewById(R.id.buttonMultBTN)
        buttonDivBTN = findViewById(R.id.buttonDivBTN)

        resultTextView = findViewById(R.id.resultTV)

        buttonResetBTN = findViewById(R.id.buttonResetBTN)
        buttonDivBTN = findViewById(R.id.buttonExitBTN)


        buttonSumBTN.setOnClickListener(this)
        buttonDifBTN.setOnClickListener(this)
        buttonMultBTN.setOnClickListener(this)
        buttonDivBTN.setOnClickListener(this)
//
        buttonResetBTN.setOnClickListener(this)
//        buttonExitBTN.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        var check = true

        if (firstOperandET.text.isEmpty() || secondOperandET.text.isEmpty()){
            return
        }

        var first = firstOperandET.text.toString().toDouble()
        var second = secondOperandET.text.toString().toDouble()

        var result = when (p0.id){
            R.id.buttonSumBTN -> Operation(first, second).sum()
            R.id.buttonDifBTN -> Operation(first, second).dif()
            R.id.buttonMultBTN -> Operation(first, second).mult()
            R.id.buttonDivBTN -> Operation(first, second).div()
            R.id.buttonResetBTN -> {
                firstOperandET.text.clear()
                secondOperandET.text.clear()
                check = false
            }

//            R.id.buttonExitBTN -> finish()
            else -> 0.0
        }

        if (!check) resultTextView.text = "Результат" else resultTextView.text = result.toString()
    }
}