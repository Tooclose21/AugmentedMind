package com.example.apptest4.helpers

import java.util.Random

fun generateNumbersFloat(min: Float, max: Float): Float {
    val rand = Random()
    return rand.nextFloat() * (max - min) + min
}

//fun generateNumbersInt(min: Int, max: Int): Int {
//    val rand = Random()
//    return rand.nextInt() * (max - min) + min
//}

fun generateNumbersInt(min: Int, max: Int): Int {
    return (Math.random() * (max - min) + min).toInt()
}
