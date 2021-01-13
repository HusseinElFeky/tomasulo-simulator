package com.husseinelfeky.tomasulosimulator.utils

import androidx.annotation.IntRange
import kotlin.math.pow
import kotlin.math.roundToInt

fun Double.roundTo(@IntRange(from = 0, to = 14) decimalPlaces: Int): Double {
    val factor = 10.0.pow(decimalPlaces.toDouble())
    return (this * factor).roundToInt() / factor
}
