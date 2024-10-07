package com.example.apptest4.computation.db

import com.example.apptest4.entity.BearPoints
import com.example.apptest4.entity.DicesPoints


interface IStore {

    suspend fun addBearPoints(userId: String, points: BearPoints, onSuccess: () -> Unit, onError: (String) -> Unit)
    suspend fun addDicesPoints(userId: String, points: DicesPoints, onSuccess: () -> Unit, onError: (String) -> Unit)
    suspend fun fetchDicesPoints(userId: String, onSuccess: (List<DicesPoints>) -> Unit, onError: (String) -> Unit)
    suspend fun fetchBearPoints(userId: String, onSuccess: (List<BearPoints>) -> Unit, onError: (String) -> Unit)
    suspend fun fetchAll(userId: String, onSuccess: (List<DicesPoints>, List<BearPoints>) -> Unit, onError: (String) -> Unit)

}