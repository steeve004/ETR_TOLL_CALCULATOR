package com.example.mapd711_test

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mapd711_test.TollStrings.Toll

class resultActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.resultactivity_resultscreen)

        val receivedObject = intent.getParcelableExtra<Toll>("myObject")

        if(receivedObject != null) {
            // Display user inputs and calculated results in TextViews
            val userInputDistanceTextView = findViewById<TextView>(R.id.userInputDistanceTextView)
            userInputDistanceTextView.text = "${receivedObject.distance} km"

            val userInputTimeOfDayTextView = findViewById<TextView>(R.id.userInputTimeOfDayTextView)
            userInputTimeOfDayTextView.text = "${receivedObject.timeOfDay}"

            val userInputVehicleSizeTextView = findViewById<TextView>(R.id.userInputVehicleSizeTextView)
            userInputVehicleSizeTextView.text = "${receivedObject.vehicleSize}"

            val userInputDirectionTextView = findViewById<TextView>(R.id.userInputDirectionTextView)
            userInputDirectionTextView.text = "${receivedObject.direction}"

            val calculatedTollTextView = findViewById<TextView>(R.id.calculatedTollTextView)
            calculatedTollTextView.text = "$${receivedObject.tollCharges}"

            val transponderTextView = findViewById<TextView>(R.id.transponderTextView)
            transponderTextView.text = "${receivedObject.transponder}"

            // Handle the WebView for loading the toll calculator link
            val tollCalculatorWebView = findViewById<WebView>(R.id.tollCalculatorWebView)
            val loadOnlineCalculator = receivedObject.loadOnline

            if (loadOnlineCalculator) {
                tollCalculatorWebView.visibility = View.VISIBLE
                val url = "https://www.407etr.com/en/tolls/tolls/toll-calculator.html"
                tollCalculatorWebView.loadUrl(url)

                tollCalculatorWebView.settings.javaScriptEnabled = true
                tollCalculatorWebView.settings.setSupportZoom(true)
            }
        }
    }
}
