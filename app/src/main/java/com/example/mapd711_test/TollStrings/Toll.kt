package com.example.mapd711_test.TollStrings

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Toll(
    val vehicleSize: String,
    val distance: String,
    val timeOfDay: String,
    val direction: String,
    val transponder: String,
    val tollCharges: String,
    val loadOnline: Boolean
): Parcelable