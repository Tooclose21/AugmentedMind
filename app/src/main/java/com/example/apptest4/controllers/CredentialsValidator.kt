package com.example.apptest4.controllers

class CredentialsValidator {

    fun validateEmail(email: String): Boolean {
        val regex = Regex("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}\$", RegexOption.IGNORE_CASE)
        return regex.matches(email)
    }

    fun validatePassword(password: String): Boolean {
        return password.length >= 8 ||
                Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$").matches(password)
    }

    fun validateRepeat(password: String, repeatPassword: String): Boolean {
        return password == repeatPassword
    }

}