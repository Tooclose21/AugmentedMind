package com.example.apptest4
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.apptest4.ui.theme.AppTest4Theme

class DicesFinishPanel : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val answer = intent.getIntExtra("answer", 0)
        val gameMode = intent.getIntExtra("gameMode", 6000)
        val dicesNumber = intent.getIntExtra("dicesNumber", 2)
        val correctAnswer = intent.getIntExtra("correctAnswer", 0)
        val isCorrect = correctAnswer == answer
        setContent {
            AppTest4Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        if (isCorrect) {
                            Text(
                                text = "Congrats! Your answer was correct",
                                fontSize = 20.sp,
                            )
                        } else {
                            Text(
                                text = "Not this time", fontSize = 20.sp
                            )
                            Text(
                                text = "Correct answer: $correctAnswer", fontSize = 20.sp
                            )
                        }
                        Button(
                            onClick = {
                                startActivity(Intent(
                                    this@DicesFinishPanel, RememberDices::class.java
                                ).also {
                                    it.putExtra("gameMode", gameMode)
                                    it.putExtra("dicesNumber", dicesNumber)
                                })
                            }, modifier = Modifier.padding(vertical = 4.dp)
                        ) {
                            Text(text = "Play again")
                        }
                        Button(
                            onClick = {
                                startActivity(
                                    Intent(
                                        this@DicesFinishPanel, ChooseGameActivity::class.java
                                    )
                                )
                            }, modifier = Modifier.padding(vertical = 4.dp)
                        ) {
                            Text(text = "Finish")
                        }
                    }
                }
            }

        }
    }
}
