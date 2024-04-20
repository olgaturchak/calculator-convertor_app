package com.example.calculator
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import org.mariuszgromada.math.mxparser.Expression


class MainActivity : AppCompatActivity() {
    private lateinit var expression: TextView
    private lateinit var result: TextView
    private lateinit var clear: Button
    private lateinit var backSpace: Button
    private lateinit var percent: Button
    private lateinit var divide: Button
    private lateinit var multiply: Button
    private lateinit var add: Button
    private lateinit var subtract: Button
    private lateinit var equal: Button
    private lateinit var dot: Button
    private lateinit var zero: Button
    private lateinit var doubleZero: Button
    private lateinit var one: Button
    private lateinit var two: Button
    private lateinit var three: Button
    private lateinit var four: Button
    private lateinit var five: Button
    private lateinit var six: Button
    private lateinit var seven: Button
    private lateinit var eight: Button
    private lateinit var nine: Button

    override fun onStop() {
        Log.d("MYapp", "onStop")
        super.onStop()
    }

    override fun onStart() {
        Log.d("MYapp", "onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.d("MYapp", "onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.d("MYapp", "onPause")
        super.onPause()
    }

    override fun onDestroy() {
        Log.d("MYapp", "onDestroy")
        super.onDestroy()
    }

    override fun onRestart() {
        Log.d("MYapp", "onRestart")
        super.onRestart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calculator)

        Log.d("MYapp", "onCreate")

        expression = findViewById(R.id.expression)
        result = findViewById(R.id.result)
        clear = findViewById(R.id.clear)
        backSpace = findViewById(R.id.backSpace)
        percent = findViewById(R.id.percent)
        divide = findViewById(R.id.divide)
        multiply = findViewById(R.id.multiply)
        add = findViewById(R.id.add)
        subtract = findViewById(R.id.subtract)
        equal = findViewById(R.id.equal)
        dot = findViewById(R.id.dot)
        zero = findViewById(R.id.zero)
        doubleZero = findViewById(R.id.Doublezero)
        one = findViewById(R.id.one)
        two = findViewById(R.id.two)
        three = findViewById(R.id.three)
        four = findViewById(R.id.four)
        five = findViewById(R.id.five)
        six = findViewById(R.id.six)
        seven = findViewById(R.id.seven)
        eight = findViewById(R.id.eight)
        nine = findViewById(R.id.nine)


        var receivedValue = intent.getStringExtra("value")
        if (receivedValue != null) {
            if (receivedValue.endsWith(".0")) {
                receivedValue = receivedValue.toString().replace(".0", "")
            }
            expression.setText(receivedValue)  // Set the value into the TextView
            result.setText(receivedValue)  // Set the value into the TextView
        }

        expression.movementMethod = ScrollingMovementMethod()
        expression.isActivated = true
        expression.isPressed = true

        var str: String



        fun addNumber(num: String) {
            expression.textSize = 60F
            result.textSize = 40F
            if (expression.text.toString().startsWith("0")) {
                str = expression.text.toString().replace("0", "") + num
                expressionText(str)
                resultText()
            } else {
                str = expression.text.toString() + num
                expressionText(str)
                resultText()
            }
        }

        fun addPercentChar(char: String) {
            expression.textSize = 60F
            result.textSize = 40F
            if (expression.text.toString().endsWith("/") ||
                expression.text.toString().endsWith("+") ||
                expression.text.toString().endsWith("-") ||
                expression.text.toString().endsWith("*") ||
                expression.text.toString().endsWith(".")
            ) {
                val lastIndex = expression.text.toString().lastIndex
                str =
                    (((expression.text.toString()
                        .substring(0, lastIndex)).toDouble()) / 100).toString()
                expressionText(str)
            } else {
                str = (((expression.text.toString()).toDouble()) / 100).toString()
                expressionText(str)
            }
            resultText()

        }

        fun addChar(char: String) {
            expression.textSize = 60F
            result.textSize = 40F
            if (expression.text.toString().endsWith("/") ||
                expression.text.toString().endsWith("+") ||
                expression.text.toString().endsWith("-") ||
                expression.text.toString().endsWith("*") ||
                expression.text.toString().endsWith(".")
            ) {
                val lastIndex = expression.text.toString().lastIndex
                str =
                    (expression.text.toString().substring(0, lastIndex)) + char
                expressionText(str)
            } else {
                str = expression.text.toString() + char
                expressionText(str)
            }
        }

        fun addDot(char: String) {
            var index = 0
            for (item in expression.text.toString().reversed()) {
                if (item == '.') {
                    return
                } else if (item == '/' || item == '*' || item == '-' || item == '+' || item == '=') {
                    if (index == 0) {
                        return
                    }
                    break
                }

             index++
            }
            str = expression.text.toString() + char

            expressionText(str)
        }



        clear.setOnClickListener {
            expressionText("0")
            expression.textSize = 60F
            result.textSize = 40F
            resultText()
        }
        backSpace.setOnClickListener {
            if (expression.text.toString().isNotEmpty()) {
                if(expression.text.toString() == "0"){
                    return@setOnClickListener
                }
                val lastIndex = expression.text.toString().lastIndex
                str = expression.text.toString().substring(0, lastIndex)
                expressionText(str)
                resultText()
            } else {
                expressionText("0")
                resultText()
            }
        }
        percent.setOnClickListener {
            addPercentChar(percent.text.toString())
        }
        divide.setOnClickListener {
            addChar(divide.text.toString())
        }
        multiply.setOnClickListener {
            addChar(multiply.text.toString())
        }
        add.setOnClickListener {
            addChar(add.text.toString())
        }
        subtract.setOnClickListener {
            addChar(subtract.text.toString())
        }
        equal.setOnClickListener {
            val exp = expression.text.toString()

            when (exp.last()) {
                '*', '-', '+', '/', '.' -> {
                    val newStr = exp.substring(0, exp.length - 1)
                    result.text = newStr
                    expression.text = newStr
                    expressionText(newStr)
                    resultText()

                }
            }
            expression.textSize = 40F
            result.textSize = 60F


        }
        dot.setOnClickListener {
            addDot(dot.text.toString())
        }

        zero.setOnClickListener {
            addNumber(zero.text.toString())
        }
        doubleZero.setOnClickListener {
            addNumber(doubleZero.text.toString())
        }
        one.setOnClickListener {
            addNumber(one.text.toString())
        }
        two.setOnClickListener {
            addNumber(two.text.toString())
        }
        three.setOnClickListener {
            addNumber(three.text.toString())
        }
        four.setOnClickListener {
            addNumber(four.text.toString())
        }
        five.setOnClickListener {
            addNumber(five.text.toString())
        }
        six.setOnClickListener {
            addNumber(six.text.toString())
        }
        seven.setOnClickListener {
            addNumber(seven.text.toString())
        }
        eight.setOnClickListener {
            addNumber(eight.text.toString())
        }
        nine.setOnClickListener {
            addNumber(nine.text.toString())
        }

    }

    @Override
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)  // Replace "your_menu_file" with the actual name
        return super.onCreateOptionsMenu(menu)
    }


    fun showExitDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Closing")
        builder.setMessage("Do you want to close the app?")
        builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            finishAffinity()
        })
        builder.setNegativeButton("No", null)
        builder.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.calc -> {
                // Handle settings click
                true
            }
            R.id.conv -> {
                val intent = Intent(this, CovertActivity::class.java)
                val res = result.text.toString()
                intent.putExtra("result", res)
                startActivity(intent)
                true
            }
            R.id.close -> {
                showExitDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun expressionText(str: String) {
        expression.text = str
    }

    @SuppressLint("SetTextI18n")
    private fun resultText() {
        val exp = Expression(expression.text.toString())
        try {
            val res = exp.calculate()
            if (res.toString().endsWith(".0")) {
                result.text = res.toString().replace(".0", "")
            } else {

                result.text = "$res"
            }
        } catch (e: Exception) {
            expression.text = expression.text.toString()
            result.text = expression.text.toString()
        }
    }





}