package com.example.apptest4.controllers

import com.example.apptest4.computation.db.FirebaseStore
import com.example.apptest4.entity.BearPoints
import com.example.apptest4.entity.DicesPoints

class StatisticsController {
    private val store = FirebaseStore()
    suspend fun fetchDicesPoints(onSuccess: (List<DicesPoints>) -> Unit, onError: (String) -> Unit){
        store.fetchDicesPoints("test", onSuccess, onError)
    }
    suspend fun fetchBearPoints(onSuccess: (List<BearPoints>) -> Unit, onError: (String) -> Unit){
        store.fetchBearPoints("test", onSuccess, onError)
    }
    suspend fun fetchAll(onSuccess: (List<DicesPoints>, List<BearPoints>) -> Unit, onError: (String) -> Unit){
        store.fetchAll("test", onSuccess, onError)
    }
}