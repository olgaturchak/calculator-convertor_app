package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter

import androidx.compose.ui.Modifier
import android.widget.AdapterView
import android.widget.Button

import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AlertDialog


class CovertActivity : AppCompatActivity() {
    private val lengthUnits = arrayOf("m", "km", "cm", "mm")
    private val weightUnits = arrayOf("gr", "kg", "mg", "t")
    private val speedUnits = arrayOf("m/s", "km/h")

    private var inputUnit = ""
    private var outputUnit = ""

    private lateinit var number: TextView
    private lateinit var tip_options: RadioGroup
    private lateinit var length: RadioButton
    private lateinit var weight: RadioButton
    private lateinit var speed: RadioButton
    private lateinit var spinner: Spinner
    private lateinit var spinner2: Spinner
    private lateinit var res: TextView


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.covert)




        number = findViewById(R.id.number)
        tip_options = findViewById(R.id.tip_options)
        length = findViewById(R.id.length)
        weight = findViewById(R.id.weight)
        speed = findViewById(R.id.speed)
        spinner = findViewById(R.id.spinner)
        spinner2 = findViewById(R.id.spinner2)
        res = findViewById(R.id.res)

        val receivedValue = intent.getStringExtra("result")
        if (receivedValue != null) {
            number.setText(receivedValue)  // Set the value into the TextView
        }




        setupSpinners()

        tip_options.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.length -> {
                    showSpinners(lengthUnits)
                }

                R.id.weight -> {
                    showSpinners(weightUnits)
                }

                R.id.speed -> {
                    showSpinners(speedUnits)
                }

                else -> {
                    spinner.visibility = View.GONE
                    spinner2.visibility = View.GONE
                }
            }
        }

    }

    private fun setupSpinners() {
        spinner.onItemSelectedListener = spinnerListener
        spinner2.onItemSelectedListener = spinnerListener
    }

    private fun showSpinners(units: Array<String>) {
        spinner.adapter = ArrayAdapter(
            this,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            units
        )
        spinner2.adapter = ArrayAdapter(
            this,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            units
        )
        spinner.visibility = View.VISIBLE
        spinner2.visibility = View.VISIBLE
    }


    private val spinnerListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            when (parent?.id) {
                R.id.spinner -> inputUnit = parent.getItemAtPosition(position).toString()
                R.id.spinner2 -> outputUnit = parent.getItemAtPosition(position).toString()
            }
            convert()
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }

    private fun convert() {
        val input = number.text.toString().toDoubleOrNull() ?: 0.0
        val result = when (inputUnit) {
            "m" -> when (outputUnit) {
                "km" -> input / 1000
                "cm" -> input * 100
                "mm" -> input * 1000
                else -> input
            }
            "km" -> when (outputUnit) {
                "m" -> input * 1000
                "cm" -> input * 100000
                "mm" -> input * 1000000
                else -> input
            }
            "cm" -> when (outputUnit) {
                "m" -> input / 100
                "km" -> input / 100000
                "mm" -> input * 10
                else -> input
            }
            "mm" -> when (outputUnit) {
                "m" -> input / 1000
                "km" -> input / 1000000
                "cm" -> input / 10
                else -> input
            }



            "gr" -> when (outputUnit) {
                "kg" -> input / 1000
                "mg" -> input * 1000
                "t" -> input / 1000000
                else -> input
            }
            "kg" -> when (outputUnit) {
                "gr" -> input * 1000
                "mg" -> input * 1000000
                "t" -> input / 1000
                else -> input
            }
            "mg" -> when (outputUnit) {
                "kg" -> input / 1000000
                "gr" -> input / 1000
                "t" -> input / 1000000000
                else -> input
            }
            "t" -> when (outputUnit) {
                "kg" -> input * 1000
                "gr" -> input * 1000000
                "mg" -> input / 1000000000
                else -> input
            }




            "m/s" -> when (outputUnit) {
                "km/h" -> input * 3.6
                else -> input
            }
            "km/h" -> when (outputUnit) {
                "m/s" -> input * 0.2778
                else -> input
            }
            else -> 0.0
        }
        res.text = "$result"

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

                val intent = Intent(this, MainActivity::class.java)
                val value = res.text.toString()
                intent.putExtra("value", value)
                startActivity(intent)
                true
            }
            R.id.conv -> {

                true
            }
            R.id.close -> {
                showExitDialog()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}