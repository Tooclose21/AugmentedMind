package com.example.apptest4.controllers

import android.util.Log
import com.example.apptest4.entity.BearPoints
import com.example.apptest4.entity.DicesPoints
import java.util.Calendar

class StatisticsManager(
    private val controller: StatisticsController,
) {
    private lateinit var dicesPoints: List<DicesPoints>
    private lateinit var bearPoints: List<BearPoints>
    suspend fun fetchAll(
        onInit: (Boolean) -> Unit
    ) {
        controller.fetchAll(
            onSuccess = { dice, bear ->
                dice.forEach{
                    Log.d("DICE", it.date.toString())
                }

                dicesPoints = dice
                bearPoints = bear
                onInit(true)
            },
            onError = { Log.e("ERROR", it) }
        )
    }

    fun provideActivity(date: Calendar): List<Int> {
    val copy = Calendar.getInstance().apply { timeInMillis = date.timeInMillis }

        val firstDay = copy.apply { set(Calendar.DAY_OF_MONTH, 1) }.get(Calendar.DAY_OF_WEEK)
        val result: MutableList<Int> = when (firstDay) {
            2 -> mutableListOf()
            3 -> mutableListOf(-1)
            4 -> mutableListOf(-1, -1)
            5 -> mutableListOf(-1, -1, -1)
            6 -> mutableListOf(-1, -1, -1, -1)
            7 -> mutableListOf(-1, -1, -1, -1, -1)
            1 -> mutableListOf(-1, -1, -1, -1, -1, -1)
            else -> {
                mutableListOf()
            }
        }
        val max = date.getActualMaximum(Calendar.DAY_OF_MONTH)
        for (i in 1..max) {
            val inactive = dicesPoints.none {
                Calendar.getInstance().apply { timeInMillis = it.date }
                    .get(Calendar.DAY_OF_YEAR) == copy.apply { set(Calendar.DAY_OF_MONTH, i) }
                    .get(Calendar.DAY_OF_YEAR) && Calendar.getInstance()
                    .apply { timeInMillis = it.date }
                    .get(Calendar.YEAR) == date.get(Calendar.YEAR)
            }
            result.add(if (inactive) 0 else 1)
        }
        while (result.size % 7 != 0) {
            result.add(-1)
        }
        return result
    }
    fun provideActivityBear(date: Calendar): List<Int> {


        val firstDay = date.apply { set(Calendar.DAY_OF_MONTH, 1) }.get(Calendar.DAY_OF_WEEK)
        val result: MutableList<Int> = when (firstDay) {
            2 -> mutableListOf()
            3 -> mutableListOf(-1)
            4 -> mutableListOf(-1, -1)
            5 -> mutableListOf(-1, -1, -1)
            6 -> mutableListOf(-1, -1, -1, -1)
            7 -> mutableListOf(-1, -1, -1, -1, -1)
            1 -> mutableListOf(-1, -1, -1, -1, -1, -1)
            else -> {
                mutableListOf()
            }
        }
        val max = date.getActualMaximum(Calendar.DAY_OF_MONTH)
        for (i in 1..max) {
            val inactive = bearPoints.none {
                Calendar.getInstance().apply { timeInMillis = it.date }
                    .get(Calendar.DAY_OF_YEAR) == date.apply { set(Calendar.DAY_OF_MONTH, i) }
                    .get(Calendar.DAY_OF_YEAR) && Calendar.getInstance()
                    .apply { timeInMillis = it.date }
                    .get(Calendar.YEAR) == date.get(Calendar.YEAR)
            }
            result.add(if (inactive) 0 else 1)
        }
        while (result.size % 7 != 0) {
            result.add(-1)
        }
        return result
    }

    fun provideMemoryStatistics(calendar: Calendar, dicesNumber: Int, mode: String): Map<String, Int> {
        val dict = mapOf(Pair("Slow", 6000), Pair("Medium", 4000), Pair("Fast", 2000))
        val mode1 = dict[mode]!!
        Log.d("Mode", mode1.toString())
        Log.d("dicesNumber", dicesNumber.toString())
        Log.d("Calendar", calendar.toString())
        val monthData = dicesPoints
            .filter {
                Calendar.getInstance().apply { timeInMillis = it.date }
                    .get(Calendar.MONTH) == calendar.get(Calendar.MONTH)
                        && Calendar.getInstance().apply { timeInMillis = it.date }
                    .get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
            }
            .filter { it.mode == mode1 }
            .filter { it.dicesNumber == dicesNumber }
        monthData.forEach{
            Log.i("Dice", it.toString()
            )
        }
        Log.d("All", monthData.size.toString())
        Log.d("Correct", monthData.filter { it.correct }.size.toString())
        Log.d("Wrong", monthData.filter { !it.correct }.size.toString())
        return mapOf(
            Pair("Correct", monthData.filter { it.correct }.size),
            Pair("Wrong", monthData.filter { !it.correct }.size)
        )
    }
}