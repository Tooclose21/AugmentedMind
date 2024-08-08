package com.example.apptest4.controllers

import com.example.apptest4.helpers.Point
import com.example.apptest4.helpers.generateNumbersInt
import kotlin.random.Random
import kotlin.random.nextInt

class RememberDicesLogic {
    private lateinit var randomList: List<Int>
    var coordinatesList = listOf(
        Point(560f, 1146f), Point(383f, 1453f),
        Point(770f, 1474f), Point(875f, 1155f), Point(128f, 1206f),
        Point(725f, 800f), Point(481f, 850f), Point(230f, 1000f)
    )
    private lateinit var answersList: List<Int>
    private var coordinatesListLength = coordinatesList.size - 1
    private lateinit var positionIndex: List<Int>

    fun rollDices(amount: Int) {
        randomList = List<Int>(amount) { generateNumbersInt(1, 6) }
        positionIndex = generateSequence {
            Random.nextInt(0..coordinatesListLength)
        }
            .distinct()
            .take(amount)
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

    fun generateAnswers(): List<Int> {
        val correctAnswer = randomList.sum()
        answersList = generateSequence {
            Random.nextInt(correctAnswer - 2.. correctAnswer + 2)
        }
            .distinct()
            .take(3)
            .toMutableList()
        if (!answersList.contains(correctAnswer)) {
            (answersList as MutableList<Int>).remove(answersList[0])
            answersList += correctAnswer
        }
        return answersList.shuffled()
    }

}