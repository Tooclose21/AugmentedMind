package com.example.apptest4.helpers

import com.example.apptest4.computation.FirebaseAuthService
import com.google.firebase.auth.FirebaseAuth

object Session {
    fun getCurrentUser(): String? {
        return FirebaseAuth.getInstance().currentUser?.email
    }
}