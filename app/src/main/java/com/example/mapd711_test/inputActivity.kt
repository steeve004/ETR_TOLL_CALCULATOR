package com.example.mapd711_test

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Switch
import android.widget.Toast
import com.example.mapd711_test.TollStrings.Toll

@SuppressLint("UseSwitchCompatOrMaterialCode")
class inputActivity : AppCompatActivity() {

    private var timedy: String? = null
    private var vehicleSize: String = LIGHT_VEHICLE
    private var cameraCharges: Double = 3.10
    private var direction: String? = EAST_BOUND
    private var enableTransponder: String = "No"
    private var onlineloading: Boolean = false
    private lateinit var tODArr: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inputactivity_main)

        val vehicleSizeRadioGroup = findViewById<RadioGroup>(R.id.vehicleSizeRadioGroup)
        val distanceEditText = findViewById<EditText>(R.id.distanceEditText)
        val timeOfDaySpinner = findViewById<Spinner>(R.id.timeOfDaySpinner)
        val directionRadioGroup = findViewById<RadioGroup>(R.id.directionRadioGroup)
        val transponderSwitch = findViewById<Switch>(R.id.transponderSwitch)
        val loadOnlineCalculatorCheckBox: CheckBox = findViewById(R.id.loadOnlineCalculatorCheckBox)
        val openCalculatorButton: Button = findViewById(R.id.openCalculatorButton)

        val itemArray = resources.getStringArray(R.array.time_of_day_array).toList()
        tODArr = ArrayList(itemArray)

        loadOnlineCalculatorCheckBox.setOnCheckedChangeListener { _, isChecked ->
            onlineloading = isChecked
        }

        // Set up a listener to respond to the user's selection of vehicle size
        vehicleSizeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            val selectedRadioButton = findViewById<RadioButton>(checkedId)
            vehicleSize = selectedRadioButton.text.toString()
        }

        distanceEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Handle the input after the user finishes typing
                val distanceText = s.toString()
                val distance = distanceText.toFloatOrNull()

                if (distance != null && distance in 0.0..24.0) {
                    // Valid input within the range
                    // You can perform further actions with the valid distance
                } else {
                    // Invalid input, display a message
                    Toast.makeText(applicationContext, "Invalid distance input", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Empty implementation
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Empty implementation
            }
        })
        val timeOfDayAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.time_of_day_array,
            android.R.layout.simple_spinner_item
        )
        timeOfDaySpinner.adapter = timeOfDayAdapter


        directionRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            val selectedRadioButton = findViewById<RadioButton>(checkedId)
            direction = selectedRadioButton.text.toString()
        }

        transponderSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                enableTransponder = "Yes"
                cameraCharges = 0.0
            } else {
                enableTransponder = "No"
                cameraCharges = 3.10
            }

            // Transponder is turned off
            // Perform actions when Transponder is off
        }

        timeOfDaySpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                timedy = tODArr[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        openCalculatorButton.setOnClickListener {
            val charge = getTollCharges(vehicleSize = vehicleSize, timeOfDay = timedy.toString(), direction = direction.toString())
            val distance = distanceEditText.text.toString()

            if(distance.isNotEmpty()) {
                val totalToll = (distance.toDouble()) / 100 * charge + 1 + cameraCharges
                val tollCharges = String.format("%.2f", totalToll)

                val toll = Toll(
                    vehicleSize = vehicleSize,
                    distance = distance,
                    timeOfDay = timedy.toString(),
                    direction = direction.toString(),
                    transponder = enableTransponder.toString(),
                    tollCharges = tollCharges,
                    loadOnline = onlineloading
                )
                val intent = Intent(this, resultActivity::class.java).apply {
                    putExtra("myObject", toll)
                }
                startActivity(intent)
            } else {
                distanceEditText.error = "Enter distance first"
                distanceEditText.performClick()
                distanceEditText.requestFocus()
            }
        }
    }

    private fun getTollCharges(vehicleSize: String, timeOfDay: String, direction: String): Double {
        val tollCharges = when (timeOfDay) {
            tODArr[0] -> {
                when (vehicleSize) {
                    LIGHT_VEHICLE -> if (direction == EAST_BOUND) 25.29 else 25.29
                    HEAVY_SINGLE_VEHICLE -> if (direction == EAST_BOUND) 50.58 else 50.58
                    else -> if (direction == EAST_BOUND) 75.87 else 75.87
                }
            }
            tODArr[1] -> {
                when (vehicleSize) {
                    LIGHT_VEHICLE -> if (direction == EAST_BOUND) 42.04 else 44.86
                    HEAVY_SINGLE_VEHICLE -> if (direction == EAST_BOUND) 84.08 else 89.72
                    else -> if (direction == EAST_BOUND) 126.12 else 134.58
                }
            }
            tODArr[2] -> {
                when (vehicleSize) {
                    LIGHT_VEHICLE -> if (direction == EAST_BOUND) 47.83 else 54.93
                    HEAVY_SINGLE_VEHICLE -> if (direction == EAST_BOUND) 95.66 else 109.86
                    else -> if (direction == EAST_BOUND) 143.49 else 164.79
                }
            }
            tODArr[3] -> {
                when (vehicleSize) {
                    LIGHT_VEHICLE -> if (direction == EAST_BOUND) 42.04 else 46.58
                    HEAVY_SINGLE_VEHICLE -> if (direction == EAST_BOUND) 84.08 else 93.16
                    else -> if (direction == EAST_BOUND) 126.12 else 139.74
                }
            }
            tODArr[4] -> {
                when (vehicleSize) {
                    LIGHT_VEHICLE -> if (direction == EAST_BOUND) 38.47 else 39.07
                    HEAVY_SINGLE_VEHICLE -> if (direction == EAST_BOUND) 76.94 else 76.94
                    else -> if (direction == EAST_BOUND) 115.41 else 117.21
                }
            }
            tODArr[5] -> {
                when (vehicleSize) {
                    LIGHT_VEHICLE -> if (direction == EAST_BOUND) 43.62 else 48.61
                    HEAVY_SINGLE_VEHICLE -> if (direction == EAST_BOUND) 97.22 else 87.24
                    else -> if (direction == EAST_BOUND) 145.83 else 130.86
                }
            }
            tODArr[6] -> {
                when (vehicleSize) {
                    LIGHT_VEHICLE -> if (direction == EAST_BOUND) 49.56 else 58.48
                    HEAVY_SINGLE_VEHICLE -> if (direction == EAST_BOUND) 116.96 else 99.12
                    else -> if (direction == EAST_BOUND) 175.44 else 148.68
                }
            }
            tODArr[7] -> {
                when (vehicleSize) {
                    LIGHT_VEHICLE -> if (direction == EAST_BOUND) 46.81 else 43.62
                    HEAVY_SINGLE_VEHICLE -> if (direction == EAST_BOUND) 93.62 else 87.24
                    else -> if (direction == EAST_BOUND) 130.86 else 140.43
                }
            }
            tODArr[8] -> {
                when (vehicleSize) {
                    LIGHT_VEHICLE -> if (direction == EAST_BOUND) 25.29 else 25.29
                    HEAVY_SINGLE_VEHICLE -> if (direction == EAST_BOUND) 50.58 else 50.58
                    else -> if (direction == EAST_BOUND) 75.87 else 75.87
                }
            }
            tODArr[9] -> {
                when (vehicleSize) {
                    LIGHT_VEHICLE -> if (direction == EAST_BOUND) 25.29 else 25.29
                    HEAVY_SINGLE_VEHICLE -> if (direction == EAST_BOUND) 50.58 else 50.58
                    else -> if (direction == EAST_BOUND) 75.87 else 75.87
                }
            }
            tODArr[10] -> {
                when (vehicleSize) {
                    LIGHT_VEHICLE -> if (direction == EAST_BOUND) 34.63 else 34.63
                    HEAVY_SINGLE_VEHICLE -> if (direction == EAST_BOUND) 69.26 else 69.26
                    else -> if (direction == EAST_BOUND) 103.89 else 103.89
                }
            }
            tODArr[11] -> {
                when (vehicleSize) {
                    LIGHT_VEHICLE -> if (direction == EAST_BOUND) 25.29 else 25.29
                    HEAVY_SINGLE_VEHICLE -> if (direction == EAST_BOUND) 50.58 else 50.58
                    else -> if (direction == EAST_BOUND) 75.87 else 75.87
                }
            }
            else -> 0.0 // Default value when timeOfDay doesn't match any known value
        }

        return tollCharges
    }

    companion object{
        private var LIGHT_VEHICLE = "Light"
        private var HEAVY_SINGLE_VEHICLE = "Heavy Single"
        private var HEAVY_MULTIPLE_VEHICLE = "Heavy Multiple"

        private var EAST_BOUND = "East Bound"
        private var WEST_BOUND = "West Bound"
    }
}











