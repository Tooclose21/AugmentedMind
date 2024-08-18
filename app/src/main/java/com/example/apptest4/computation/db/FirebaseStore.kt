package com.example.apptest4.computation.db

import com.example.apptest4.entity.BearPoints
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FirebaseStore : IStore {

    private val database = Firebase.firestore
    override suspend fun addBearPoints(
        userId: String, points: BearPoints, onSuccess: () -> Unit, onError: (String) -> Unit
    ): Unit = withContext(Dispatchers.IO) {
        database.collection("Bear_$userId").document(points.date.toString()).set(points)
            .addOnFailureListener { onError(it.message ?: "unknown message") }
            .addOnSuccessListener { onSuccess() }
    }
}