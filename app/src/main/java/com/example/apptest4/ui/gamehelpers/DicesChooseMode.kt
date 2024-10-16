package com.example.apptest4.ui.gamehelpers

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apptest4.R
import com.example.apptest4.ui.games.RememberDices
import com.example.apptest4.ui.theme.AppTest4Theme
import com.example.apptest4.ui.theme.DarkGreen

class DicesChooseMode : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dicesNumber = intent.getIntExtra("dicesNumber", 2)
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
                            text = "Choose game mode",
                            style = MaterialTheme.typography.displayLarge,
                            fontSize = 65.sp,
                            color = DarkGreen
                        )
                        fun startRememberDicesActivity(dicesNumber: Int, gameMode: Int) {
                            val intent = Intent(this@DicesChooseMode, RememberDices::class.java).apply {
                                putExtra("dicesNumber", dicesNumber)
                                putExtra("gameMode", gameMode)
                            }
                            startActivity(intent)
                        }

                        ImageButton(
                            imageResId = R.drawable.slow_button,
                            onClick = { startRememberDicesActivity(dicesNumber, 6000) }
                        )
                        ImageButton(
                            imageResId = R.drawable.medium_button,
                            onClick = { startRememberDicesActivity(dicesNumber, 4000) }
                        )
                        ImageButton(
                            imageResId = R.drawable.fast_button,
                            onClick = { startRememberDicesActivity(dicesNumber, 2000) }
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun ImageButton(imageResId: Int, onClick: () -> Unit) {
    Image(
        painter = painterResource(id = imageResId),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable(onClick = onClick)
            .clip(RoundedCornerShape(16.dp))
    )
}