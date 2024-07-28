package com.example.apptest4.computation

import com.example.apptest4.entity.LoginCredentials
import com.example.apptest4.entity.RegisterCredentials
import com.google.firebase.auth.FirebaseAuth

class FirebaseAuthService : IAuthService {
    override fun registerUser(
        credentials: RegisterCredentials,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(credentials.email, credentials.password)
            .addOnCompleteListener {
                if (!it.isSuccessful) {
                    onError(it.exception!!.message.toString())
                    return@addOnCompleteListener
                }
                FirebaseAuth.getInstance().signOut()
                onSuccess()
            }

    }

    override fun loginUser(
        loginCredentials: LoginCredentials,
        onWrongCredentials: (String) -> Unit,
        onValidCredentials: (String) -> Unit
    ) {
        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(loginCredentials.email, loginCredentials.password)
            .addOnCompleteListener {
                if (!it.isSuccessful) {
                    onWrongCredentials(it.exception!!.message.toString())
                    return@addOnCompleteListener
                }
                onValidCredentials(FirebaseAuth.getInstance().currentUser!!.email.toString())
            }
    }

}