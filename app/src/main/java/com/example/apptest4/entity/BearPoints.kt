package com.example.apptest4.entity


data class BearPoints(
    val score: Int = 0,
    val date: Long = 0L,
    val timeArray: List<Long> = listOf(),
    val timeMode: Int = 10000
)
