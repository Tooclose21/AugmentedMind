package com.example.apptest4.controllers

import android.util.Log
import com.example.apptest4.helpers.generateNumbersInt

private const val dice_1 = "models/dice1.glb"
private const val dice_2 = "models/dice2.glb"
private const val dice_3 = "models/dice3.glb"
private const val dice_4 = "models/dice4.glb"
private const val dice_5 = "models/dice5.glb"
private const val dice_6 = "models/dice6.glb"

class RememberDicesLogic {
            fun rollDices (amount: Int) {
                val randomList = List<Int>(amount) { generateNumbersInt(1, 6) }
                randomList.forEachIndexed { index, item ->
                    Log.d("DICES", "At $index is $item")
                }
            }
}