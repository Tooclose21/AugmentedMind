package com.example.apptest4

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.example.apptest4.computation.db.FirebaseStore
import com.example.apptest4.entity.BearPoints
import com.example.apptest4.ui.theme.AppTest4Theme
import com.example.apptest4.ui.theme.Orange
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.Calendar

class FinishedGameActivity : ComponentActivity() {
    private val store = FirebaseStore()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTest4Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val gamePoints = intent.getIntExtra("gamePoints", 0)
                    val deathTime = intent.getLongArrayExtra("deathOfBear")
                    val gameTime = intent.getIntExtra("gameTime", 10000)
                    Log.d("ARR", deathTime.toString())
                    lifecycleScope.save(
                        gamePoints,
                        gameTime,
                        deathTime?.toList() ?: listOf(),
                        { Log.e("ERROR", it) },
                        {
                            Toast.makeText(
                                this, "points saved",
                                Toast.LENGTH_LONG
                            ).show()
                        })
                    GameFinished(gamePoints)
                }
            }
        }
    }

    @Composable
    fun GameFinished(gamePoints: Int, modifier: Modifier = Modifier) {
        Column(
            modifier = Modifier
                .systemBarsPadding()
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Good job!",
                style = MaterialTheme.typography.displayMedium,
                modifier = modifier
            )
            Text(
                text = "Your streak:",
                style = MaterialTheme.typography.displayMedium,
                modifier = modifier
            )
            Text(
                text = "$gamePoints",
                style = MaterialTheme.typography.displayLarge,
                modifier = modifier,
                color = Orange
            )
            Button(onClick = {
                startActivity(
                    Intent(
                        this@FinishedGameActivity,
                        CatchTheBear::class.java
                    )
                )
            }) {
                Text(text = "Play again", style = MaterialTheme.typography.displaySmall)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                startActivity(
                    Intent(
                        this@FinishedGameActivity,
                        ChooseGameActivity::class.java
                    )
                )
            }) {
                Text(style = MaterialTheme.typography.displaySmall, text = "Return")
            }
        }
    }

    private fun CoroutineScope.save(
        gamePoints: Int, gameTime: Int, timeArray: List<Long>, onError: (String) -> Unit,
        onSuccess: () -> Unit
    ) = launch {
        store.addBearPoints(
            "test", BearPoints(
                score = gamePoints, timeArray = timeArray,
                date = Calendar.getInstance().timeInMillis,
                timeMode = gameTime
            ),
            onSuccess, onError
        )
    }
}


