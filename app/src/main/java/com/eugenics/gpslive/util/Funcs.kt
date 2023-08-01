package com.eugenics.gpslive.util

import com.eugenics.gpslive.core.LathLong
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.absoluteValue

fun convertCoordinateToSting(value: Double, lathLong: LathLong): String {
    val direction = if (value >= 0) Pair("N", "E") else Pair("S", "W")
    val deg = value.absoluteValue.toInt()
    val min = ((value.absoluteValue - deg) * 60.0)
    val sec = (min - min.toInt()) * 60.0

    val minStr = if (min.toInt() < 10) {
        "0${min.toInt()}"
    } else {
        "${min.toInt()}"
    }

    val secBig = BigDecimal.valueOf(sec)

    val secStr = if (sec.toInt() < 10) {
        "0${secBig.setScale(3,RoundingMode.HALF_UP)}"
    } else {
        "${secBig.setScale(3,RoundingMode.HALF_UP)}"
    }

    val char = when (lathLong) {
        LathLong.LATH -> direction.first
        else -> direction.second
    }

    return "$deg°$minStr'$secStr\"$char"
}