package com.example.apptest4

import android.content.Intent
import android.os.Bundle
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
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.apptest4.computation.FirebaseAuthService
import com.example.apptest4.controllers.AuthController
import com.example.apptest4.entity.LoginCredentials
import com.example.apptest4.ui.theme.AppTest4Theme

class LoginActivity : ComponentActivity() {
    private val authController = AuthController(FirebaseAuthService())

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                                        text = "AugmentedMind",
                                        color = MaterialTheme.colorScheme.onSecondary,
                                    )
                                }, colors = TopAppBarDefaults.topAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.secondaryContainer
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(10.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Login",
                fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontFamily = MaterialTheme.typography.titleLarge.fontFamily
            )
            Spacer(modifier = Modifier.weight(2f))
            OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "E-Mail") },
                singleLine = true,
                value = login,
                onValueChange = { login = it })

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Password") },
                singleLine = true,
                value = password,
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = { password = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            Button(onClick = {
                startActivity(Intent(
                    this@LoginActivity, ChooseGameActivity::class.java
                ))
//                onLogin(LoginCredentials(login, password), onWrongCredentials = {
//                    Toast.makeText(this@LoginActivity, it, Toast.LENGTH_SHORT).show()
//                }, onValidCredentials = { uid ->
//                    startActivity(Intent(
//                        this@LoginActivity, ChooseGameActivity::class.java
//                    ).also {
//                        it.putExtra("uid", uid)
//                    })
//                    finish()
//                })
            }) {
                Text(text = "Login")
            }

            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Don't have an account?",
                    fontWeight = MaterialTheme.typography.labelLarge.fontWeight,
                    fontSize = MaterialTheme.typography.labelLarge.fontSize,
                    fontFamily = MaterialTheme.typography.labelLarge.fontFamily,
                )

                Text(text = "Register",
                    color = Color.Blue,
                    fontWeight = MaterialTheme.typography.labelLarge.fontWeight,
                    fontSize = MaterialTheme.typography.labelLarge.fontSize,
                    fontFamily = MaterialTheme.typography.labelLarge.fontFamily,
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

