package com.example.apptest4.computation

import com.example.apptest4.entity.LoginCredentials
import com.example.apptest4.entity.RegisterCredentials

interface IAuthService {
    fun registerUser(
        credentials: RegisterCredentials,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )

    fun loginUser(
        loginCredentials: LoginCredentials,
        onWrongCredentials: (String) -> Unit,
        onValidCredentials: (String) -> Unit
    )
}