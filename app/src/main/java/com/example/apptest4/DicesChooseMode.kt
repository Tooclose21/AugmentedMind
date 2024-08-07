package com.example.apptest4

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.apptest4.ui.theme.AppTest4Theme

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
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ImageButton(
                            imageResId = R.drawable.slimak,
                            onClick = { startActivity(
                                Intent(
                                this@DicesChooseMode,
                                RememberDices::class.java
                            ).also {
                                it.putExtra("dicesNumber", dicesNumber)
                                    it.putExtra("gameMode", 6000)
                            }) }
                        )
                        ImageButton(
                            imageResId = R.drawable.zajac,
                            onClick = { startActivity(
                                Intent(
                                    this@DicesChooseMode,
                                    RememberDices::class.java
                                ).also {
                                    it.putExtra("dicesNumber", dicesNumber)
                                    it.putExtra("gameMode", 4000)
                                }) }
                        )
                        ImageButton(
                            imageResId = R.drawable.wyscigowka,
                            onClick = { startActivity(
                                Intent(
                                    this@DicesChooseMode,
                                    RememberDices::class.java
                                ).also {
                                    it.putExtra("dicesNumber", dicesNumber)
                                    it.putExtra("gameMode", 2000)
                                }) }
                        )
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
        )
    }
}
