package com.example.android_calculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var toolbarMain: Toolbar

    private lateinit var inputTV: TextView
    private lateinit var resultTV: TextView

    private lateinit var numberOneBTN: Button
    private lateinit var numberTwoBTN: Button
    private lateinit var numberThreeBTN: Button
    private lateinit var numberFourBTN: Button
    private lateinit var numberFiveBTN: Button
    private lateinit var numberSixBTN: Button
    private lateinit var numberSevenBTN: Button
    private lateinit var numberEightBTN: Button
    private lateinit var numberNineBTN: Button
    private lateinit var numberZeroBTN: Button

    private lateinit var buttonSumBTN: Button
    private lateinit var buttonDifBTN: Button
    private lateinit var buttonMultBTN: Button
    private lateinit var buttonDivBTN: Button

    private val  actionButtons: MutableList<Button> = mutableListOf()

    private lateinit var equalsBTN: Button

    private lateinit var resetBTN: Button

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        toolbarMain = findViewById(R.id.toolbarMain)
        setSupportActionBar(toolbarMain)
        title = "Калькулятор"
//        toolbarMain.subtitle = "version 1.0"
//        toolbarMain.setLogo(R.drawable.baseline_calculate_24)

        inputTV = findViewById(R.id.inputTV)
        resultTV = findViewById(R.id.resultTV)

        numberOneBTN = findViewById(R.id.numberOneBTN)
        numberTwoBTN = findViewById(R.id.numberTwoBTN)
        numberThreeBTN = findViewById(R.id.numberThreeBTN)
        numberFourBTN = findViewById(R.id.numberFourBTN)
        numberFiveBTN = findViewById(R.id.numberFiveBTN)
        numberSixBTN = findViewById(R.id.numberSixBTN)
        numberSevenBTN = findViewById(R.id.numberSevenBTN)
        numberEightBTN = findViewById(R.id.numberEightBTN)
        numberNineBTN = findViewById(R.id.numberNineBTN)
        numberZeroBTN = findViewById(R.id.numberZeroBTN)

        buttonSumBTN = findViewById(R.id.buttonSumBTN)
        buttonDifBTN = findViewById(R.id.buttonDifBTN)
        buttonMultBTN = findViewById(R.id.buttonMultBTN)
        buttonDivBTN = findViewById(R.id.buttonDivBTN)

        equalsBTN = findViewById(R.id.equalsBTN)

        resetBTN = findViewById(R.id.resetBTN)

        actionButtons.addAll(listOf(numberOneBTN, numberTwoBTN, numberThreeBTN, numberFourBTN, numberZeroBTN,
                                    numberFiveBTN, numberSixBTN, numberSevenBTN, numberEightBTN, numberNineBTN,
                                    buttonSumBTN, buttonDifBTN, buttonMultBTN, buttonDivBTN))

        for (btn in actionButtons){
            btn.setOnClickListener(actionButtonClickListener)
        }

        equalsBTN.setOnClickListener {
            if (inputTV.text.isEmpty() || !inputTV.text.contains("[0-9+-/*]".toRegex())
                || inputTV.text.filterNot { it.isDigit() }.count() > 1){
                return@setOnClickListener
            }

            val action: String = inputTV.text.toString()
            inputTV.text = "$action="

            var positionOperation = action.indexOfAny(charArrayOf('+','-','/','*'))
            var first = action.subSequence(0, positionOperation).toString().toDouble()
            var operation = action[positionOperation].toString()
            var second = action.subSequence(positionOperation + 1, action.length).toString().toDouble()

            var result = when (operation){
                "+" -> Operation(first, second).sum()
                "-" -> Operation(first, second).dif()
                "*" -> Operation(first, second).mult()
                "/" -> Operation(first, second).div()
                else -> 0.0
            }

            resultTV.text = "$result"
        }

        resetBTN.setOnClickListener { view ->
            inputTV.setText("")
            inputTV.hint = "input"
            resultTV.setText("")
            resultTV.hint = "result"
            Toast.makeText(
                applicationContext,
                "Данные очищены",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    @SuppressLint("SetTextI18n")
    private val actionButtonClickListener = View.OnClickListener { v ->
        val btn = v as Button
        if (actionButtons.contains(btn)){
            val inputString = inputTV.text
            inputTV.text = "${inputString}${getButtonAction(btn)}"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        toolbarMain.menu.findItem(R.id.exitMenuMain).setIcon(R.drawable.exit_to_app)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.exitMenuMain -> {
                Toast.makeText(
                    applicationContext,
                    "Работа завершена",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getButtonAction(button: Button): String{
        return button.text.toString()
    }
}