package com.example.apptest4.computation.db

import com.example.apptest4.entity.BearPoints


interface IStore {

    suspend fun addBearPoints(userId: String, points: BearPoints, onSuccess: () -> Unit, onError: (String) -> Unit)

}