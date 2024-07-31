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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.apptest4.computation.FirebaseAuthService
import com.example.apptest4.controllers.AuthController
import com.example.apptest4.controllers.CredentialsValidator
import com.example.apptest4.entity.RegisterCredentials
import com.example.apptest4.ui.theme.AppTest4Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class RegisterActivity : ComponentActivity() {

    private val authController = AuthController(FirebaseAuthService())

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val snackbarHostState = remember { SnackbarHostState() }
            val scope = rememberCoroutineScope()

            AppTest4Theme(
                darkTheme = false, dynamicColor = false
            ) {

                Scaffold(
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = "HealthEndar",
                                    fontFamily = MaterialTheme.typography.titleLarge.fontFamily,
                                    fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                    color = MaterialTheme.colorScheme.onSecondary,
                                )
                            }, colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer
                            )
                        )
                    },
                    modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer),
                ) {
                    Box(
                        modifier = Modifier.padding(it),

                        ) {
                        RegisterView(scope, snackbarHostState)

                    }
                }
            }
        }
    }

    @Composable
    private fun RegisterView(scope: CoroutineScope, snackbarHostState: SnackbarHostState) {
        var username by remember {
            mutableStateOf("")
        }
        var email by remember {
            mutableStateOf("")
        }

        var password by remember {
            mutableStateOf("")
        }
        var repeatPassword by remember {
            mutableStateOf("")
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(10.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Register",
                fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontFamily = MaterialTheme.typography.titleLarge.fontFamily
            )
            Spacer(modifier = Modifier.weight(1f))
            OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = { Text(text = "Login") },
                value = username,
                onValueChange = { username = it })

            OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = { Text(text = "Email") },
                value = email,
                onValueChange = { email = it })

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Password") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                value = password,
                onValueChange = { password = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                label = { Text(text = "Repeat password") },
                value = repeatPassword,
                onValueChange = { repeatPassword = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            Button(onClick = {
                onRegister(RegisterCredentials(
                    username.trim { it <= ' ' },
                    email.trim { it <= ' ' },
                    password.trim { it <= ' ' },
                    repeatPassword.trim { it <= ' ' }
                ), onWrongCredentials = {
                    Toast.makeText(this@RegisterActivity, it, Toast.LENGTH_SHORT).show()
                }, onSuccessRegistration = {
                    startActivity(
                        Intent(
                            this@RegisterActivity, LoginActivity::class.java
                        )
                    )
                    finish()
                })
            }) {
                Text(text = "Register")
            }

            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Have an account?",
                    fontWeight = MaterialTheme.typography.labelLarge.fontWeight,
                    fontSize = MaterialTheme.typography.labelLarge.fontSize,
                    fontFamily = MaterialTheme.typography.labelLarge.fontFamily,
                )

                Text(text = "Login",
                    color = Color.Blue,
                    fontWeight = MaterialTheme.typography.labelLarge.fontWeight,
                    fontSize = MaterialTheme.typography.labelLarge.fontSize,
                    fontFamily = MaterialTheme.typography.labelLarge.fontFamily,
                    modifier = Modifier.clickable {
                        startActivity(
                            Intent(
                                this@RegisterActivity, LoginActivity::class.java
                            )
                        )
                    })
            }
            Spacer(modifier = Modifier.weight(1f))
        }


    }

    private fun onRegister(
        registerCredentials: RegisterCredentials,
        onWrongCredentials: (String) -> Unit,
        onSuccessRegistration: (String) -> Unit
    ) {

        val validation = CredentialsValidator()

        if (!validation.validateEmail(registerCredentials.email)) {
            onWrongCredentials("Wrong email")
            return
        }

        if (!validation.validatePassword(registerCredentials.password)) {
            onWrongCredentials("Wrong password")
            return
        }

        if (!validation.validateRepeat(
                registerCredentials.password, registerCredentials.repeatPassword
            )
        ) {
            onWrongCredentials("Password not matching")
            return
        }

        authController.registerUser(credentials = registerCredentials,
            onSuccess = { onSuccessRegistration("Successfully registered user: ${registerCredentials.username}") },
            onError = { onWrongCredentials(it) })
    }

}
