package com.example.apptest4.ui.gamehelpers

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.apptest4.ui.games.CatchTheBear
import com.example.apptest4.ui.theme.AppTest4Theme
import com.example.apptest4.ui.theme.Orange

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
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = "Choose game mode",
                            style = MaterialTheme.typography.displayLarge,
                            color = Orange
                        )
                        fun startCatchTheBear(gameTime: Int) {
                            val intent = Intent(this@CatchTheBearTimeMode, CatchTheBear::class.java).apply {
                                putExtra("gameTime", gameTime)
                            }
                            startActivity(intent)
                        }
                        Spacer(modifier = Modifier.height(50.dp))
                        Button(
                            onClick = { startCatchTheBear(10000)},
                            modifier = Modifier.padding(vertical = 4.dp).height(120.dp)
                        ) {
                            Text(text = "3 min", style = MaterialTheme.typography.displayMedium)
                        }
                        Spacer(modifier = Modifier.height(30.dp))
                        Button(
                            onClick = { startCatchTheBear(300000)},
                            modifier = Modifier.padding(vertical = 4.dp).height(120.dp)
                        ) {
                            Text(text = "5 min", style = MaterialTheme.typography.displayMedium)
                        }
                        Spacer(modifier = Modifier.height(30.dp))
                        Button(
                            onClick = { startCatchTheBear(600000)},
                            modifier = Modifier.padding(vertical = 4.dp).height(130.dp)
                        ) {
                            Text(text = "10 min", style = MaterialTheme.typography.displayMedium)
                        }
                    }
                }
            }
        }
    }
}