package com.example.apptest4.controllers

import com.example.apptest4.computation.IAuthService
import com.example.apptest4.entity.LoginCredentials
import com.example.apptest4.entity.RegisterCredentials

class AuthController(
    private val service: IAuthService
) {
    fun registerUser(
        credentials: RegisterCredentials,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        service.registerUser(credentials, onSuccess, onError)
    }

    fun loginUser(
        loginCredentials: LoginCredentials,
        onWrongCredentials: (String) -> Unit,
        onValidCredentials: (String) -> Unit
    ) {
        service.loginUser(loginCredentials, onWrongCredentials, onValidCredentials)
    }
}