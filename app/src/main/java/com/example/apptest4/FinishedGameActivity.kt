package com.example.apptest4

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.apptest4.ui.theme.AppTest4Theme

class FinishedGameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTest4Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val gamePoints = intent.getIntExtra("gamePoints", 0)
                    GameFinished(gamePoints)
                }
            }
        }
    }
    @Composable
    fun GameFinished(gamePoints: Int, modifier: Modifier = Modifier) {
        Column() {
            Text(
                text = "Needed a break?",
                modifier = modifier
            )
            Text(
                text = "Your streak:",
                modifier = modifier
            )
            Text(
                text = "$gamePoints",
                modifier = modifier
            )
            Button(onClick = { startActivity(Intent(this@FinishedGameActivity,
                MainActivity::class.java)) }) {
                Text(fontSize = 20.sp, text = "Play again")
            }
            Button(onClick = { /*TODO*/ }) {
                Text(fontSize = 20.sp, text = "Return")
            }
        }
    }
}


