package com.example.apptest4.controllers

import android.util.Log
import com.example.apptest4.helpers.Point
import com.example.apptest4.helpers.generateNumbersInt

class RememberDicesLogic {
    private lateinit var randomList: List<Int>
    private lateinit var positionIndex: List<Int>
    var coordinatesList = listOf(Point(560f, 1146f), Point(383f, 1453f),
        Point(770f, 1474f), Point(875f, 1155f),
        Point(676f, 739f), Point(481f, 705f))
    fun rollDices(amount: Int) {
        randomList = List<Int>(amount) { generateNumbersInt(1, 6) }
    }
    fun assignDicesNames(): List<String> {
        return randomList.map {
            "dice$it.glb"
        }.toList()
    }

}