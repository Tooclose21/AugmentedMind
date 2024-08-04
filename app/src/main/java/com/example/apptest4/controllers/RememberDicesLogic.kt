package com.example.apptest4.controllers

import android.util.Log
import com.example.apptest4.helpers.generateNumbersInt

class RememberDicesLogic {
    private lateinit var randomList: List<Int>
    fun rollDices(amount: Int) {
        randomList = List<Int>(amount) { generateNumbersInt(1, 6) }
//        randomList.forEachIndexed { index, item ->
//            Log.d("DICES", "At $index is $item")
//        }
    }
    fun assignDicesNames(): List<String> {
        return randomList.map {
            "dice$it.glb"
        }.toList()
    }

}