package com.example.apptest4

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.example.apptest4.computation.db.FirebaseStore
import com.example.apptest4.entity.BearPoints
import com.example.apptest4.ui.theme.AppTest4Theme
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
                    lifecycleScope.save(gamePoints, { Log.e("ERROR", it)}, {Toast.makeText(this, "points saved",
                        Toast.LENGTH_LONG).show()})
                    GameFinished(gamePoints)
                }
            }
        }
    }
    @Composable
    fun GameFinished(gamePoints: Int, modifier: Modifier = Modifier) {
        Column(modifier = Modifier
            .systemBarsPadding()
            .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
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
                CatchTheBear::class.java)) }) {
                Text(fontSize = 20.sp, text = "Play again")
            }
            Button(onClick = { startActivity(Intent(this@FinishedGameActivity,
                ChooseGameActivity::class.java)) }) {
                Text(fontSize = 20.sp, text = "Return")
            }
        }
    }

    private fun CoroutineScope.save(gamePoints: Int, onError: (String) -> Unit, onSuccess: () -> Unit) = launch {
        store.addBearPoints("test", BearPoints(score = gamePoints, date = Calendar.getInstance().timeInMillis),
            onSuccess, onError)
    }
}


