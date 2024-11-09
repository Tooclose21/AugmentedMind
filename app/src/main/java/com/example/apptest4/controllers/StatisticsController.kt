package com.example.apptest4.controllers

import com.example.apptest4.computation.db.FirebaseStore
import com.example.apptest4.entity.BearPoints
import com.example.apptest4.entity.DicesPoints
import com.example.apptest4.helpers.Session

class StatisticsController {
    private val store = FirebaseStore()
    suspend fun fetchDicesPoints(onSuccess: (List<DicesPoints>) -> Unit, onError: (String) -> Unit){
        store.fetchDicesPoints(Session.getCurrentUser()!!, onSuccess, onError)
    }
    suspend fun fetchBearPoints(onSuccess: (List<BearPoints>) -> Unit, onError: (String) -> Unit){
        store.fetchBearPoints(Session.getCurrentUser()!!, onSuccess, onError)
    }
    suspend fun fetchAll(onSuccess: (List<DicesPoints>, List<BearPoints>) -> Unit, onError: (String) -> Unit){
        store.fetchAll(Session.getCurrentUser()!!, onSuccess, onError)
    }
}