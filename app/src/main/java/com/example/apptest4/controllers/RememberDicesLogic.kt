package com.example.apptest4.controllers

import android.util.Log
import com.example.apptest4.helpers.Point
import com.example.apptest4.helpers.generateNumbersInt
import dev.romainguy.kotlin.math.length
import kotlin.random.Random
import kotlin.random.nextInt

class RememberDicesLogic {
    private lateinit var randomList: List<Int>
    var coordinatesList = listOf(Point(560f, 1146f), Point(383f, 1453f),
        Point(770f, 1474f), Point(875f, 1155f), Point(128f, 1206f),
        Point(725f, 800f), Point(481f, 850f), Point(230f, 1000f))
    private var coordinatesListLength = coordinatesList.size - 1
    private lateinit var positionIndex: List<Int>

    fun rollDices(amount: Int) {
        randomList = List<Int>(amount) { generateNumbersInt(1, 6) }
        positionIndex = generateSequence {
            Random.nextInt(0..coordinatesListLength)
        }
            .distinct()
            .take(2)
            .toList()
    }
    fun assignDicesNames(): List<String> {
        return randomList.map {
            "dice$it.glb"
        }.toList()
    }
    fun getPositionAt(position: Int): Point {
           return coordinatesList[positionIndex[position]]
    }
}