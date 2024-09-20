package com.example.apptest4

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.unit.dp
import com.example.apptest4.ui.theme.AppTest4Theme
import com.example.apptest4.ui.theme.DarkGreen

class CatchTheBearTimeMode : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTest4Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(40.dp),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Choose game time",
                            style = MaterialTheme.typography.displayLarge,
                            color = DarkGreen
                        )
                        fun startCatchTheBear(gameTime: Int) {
                            val intent = Intent(this@CatchTheBearTimeMode, CatchTheBear::class.java).apply {
                                putExtra("gameTime", gameTime)
                            }
                            startActivity(intent)
                        }

                        Button(
                            onClick = { startCatchTheBear(10000)},
                            modifier = Modifier.padding(vertical = 4.dp)
                        ) {
                            Text(text = "3 min", style = MaterialTheme.typography.displayMedium)
                        }
                        Button(
                            onClick = { startCatchTheBear(10000)},
                            modifier = Modifier.padding(vertical = 4.dp)
                        ) {
                            Text(text = "5 min", style = MaterialTheme.typography.displayMedium)
                        }
                        Button(
                            onClick = { startCatchTheBear(10000)},
                            modifier = Modifier.padding(vertical = 4.dp)
                        ) {
                            Text(text = "10 min", style = MaterialTheme.typography.displayMedium)
                        }
                    }
                }
            }
        }
    }
}