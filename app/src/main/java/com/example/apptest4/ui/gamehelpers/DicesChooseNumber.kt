package com.example.apptest4.ui.gamehelpers

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.apptest4.ui.theme.AppTest4Theme
import com.example.apptest4.ui.theme.DarkGreen
import com.example.apptest4.ui.theme.GreenHighlight

class DicesChooseNumber : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTest4Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DiceChooserScreen()
                }
            }
        }
    }
    @Composable
    fun DiceChooserScreen() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Choose dices number",
                style = MaterialTheme.typography.displayLarge,
                color = DarkGreen,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(42.dp))
            DiceButtonsGrid()
        }
    }

    @Composable
    fun DiceButtonsGrid() {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            DiceButton(2)
            DiceButton(3)
            DiceButton(4)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DiceButton(5)
            DiceButton(6)
        }
    }

    @Composable
    fun DiceButton(number: Int) {
        Button(
            onClick = { startActivity(Intent(
                this@DicesChooseNumber,
                DicesChooseMode::class.java
            ).also {
                it.putExtra("dicesNumber", number)
            })},
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = GreenHighlight),
            modifier = Modifier
                .size(100.dp)
                .padding(8.dp)
        ) {
            Text(
                text = number.toString(),
                style = MaterialTheme.typography.displayMedium,
                color = Color.White
            )
        }
    }
}

