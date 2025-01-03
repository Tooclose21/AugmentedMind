package com.example.apptest4.ui.auth

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.apptest4.ui.gamehelpers.ChooseGameActivity
import com.example.apptest4.computation.FirebaseAuthService
import com.example.apptest4.controllers.AuthController
import com.example.apptest4.entity.BearPoints
import com.example.apptest4.entity.DicesPoints
import com.example.apptest4.entity.LoginCredentials
import com.example.apptest4.ui.theme.AppTest4Theme
import com.example.apptest4.ui.theme.DarkGreen
import com.example.apptest4.ui.theme.DarkGray
import com.example.apptest4.ui.theme.LightBack
import com.example.apptest4.ui.theme.Orange
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class LoginActivity : ComponentActivity() {
    private val authController = AuthController(FirebaseAuthService())

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val notificationChannel = NotificationChannel(
            "Reminder",
            "Reminder",
            NotificationManager.IMPORTANCE_HIGH
        )

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(notificationChannel)
        setContent {
            AppTest4Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(
                                        style = MaterialTheme.typography.displayMedium,
                                        text = "AugmentedMind",
                                        color = Orange,
                                    )
                                }, colors = TopAppBarDefaults.topAppBarColors(
                                    containerColor = LightBack
                                )
                            )
                        },
                        modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer),
                    ) {
                        Box(modifier = Modifier.padding(it)) {
                            LoginView()

                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun LoginView() {
        var login by remember {
            mutableStateOf("")
        }

        var password by remember {
            mutableStateOf("")
        }
        var passwordVisible by rememberSaveable { mutableStateOf(false) }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(10.dp)
        ) {
            Spacer(modifier = Modifier.weight(2f))
            Text(
                text = "Log in",
                style = MaterialTheme.typography.displayLarge,
                color = DarkGreen
            )
            Spacer(modifier = Modifier.weight(0.5f))
            OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "E-Mail", style = MaterialTheme.typography.titleLarge) },
                singleLine = true,
                textStyle = MaterialTheme.typography.titleLarge,
                value = login,
                onValueChange = { login = it })

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Password", style = MaterialTheme.typography.titleLarge) },
                singleLine = true,
                textStyle = MaterialTheme.typography.titleLarge,
                value = password,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                onValueChange = { password = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff
                    val description = if (passwordVisible) "Hide password" else "Show password"

                    IconButton(onClick = {passwordVisible = !passwordVisible}){
                        Icon(imageVector  = image, description)
                    }}
            )
            Spacer(modifier = Modifier.weight(0.3f))
            Button( colors = ButtonDefaults.buttonColors(containerColor = DarkGreen),
                onClick = {
//                startActivity(Intent(
//                    this@LoginActivity, ChooseGameActivity::class.java
//                ))
                onLogin(LoginCredentials(login, password), onWrongCredentials = {
                    Toast.makeText(this@LoginActivity, it, Toast.LENGTH_SHORT).show()
                }, onValidCredentials = { uid ->
                    startActivity(Intent(
                        this@LoginActivity, ChooseGameActivity::class.java
                    ).also {
                        it.putExtra("uid", uid)
                    })
                    finish()
                })
            }) {
                Text(text = "Login", style = MaterialTheme.typography.titleLarge)
            }

            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Don't have an account?",
                    style = MaterialTheme.typography.headlineLarge,
                    color = DarkGray
                )

                Text(text = " Register",
                    color = Orange,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.clickable {
                        startActivity(
                            Intent(
                                this@LoginActivity, RegisterActivity::class.java
                            )
                        )
                    })
            }

            Spacer(modifier = Modifier.weight(1f))

        }
    }

    private fun onLogin(
        loginCredentials: LoginCredentials,
        onWrongCredentials: (String) -> Unit,
        onValidCredentials: (String) -> Unit
    ) {
        if (loginCredentials.email.isBlank()) {
            onWrongCredentials("Login is empty")
            return
        }

        if (loginCredentials.password.isBlank()) {
            onWrongCredentials("Password is empty")
            return
        }

        authController.loginUser(loginCredentials, onWrongCredentials, onValidCredentials)
    }
}

