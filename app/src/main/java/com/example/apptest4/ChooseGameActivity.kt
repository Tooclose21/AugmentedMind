package com.example.apptest4

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastForEachIndexed
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.apptest4.helpers.Exercise
import com.example.apptest4.helpers.More
import com.example.apptest4.helpers.NavbarItem
import com.example.apptest4.helpers.Stats
import com.example.apptest4.ui.theme.AppTest4Theme
import com.example.apptest4.ui.theme.DarkGrey
import com.example.apptest4.ui.theme.DarkOrange
import com.example.apptest4.ui.theme.Orange
import com.example.apptest4.ui.theme.OrangeHighlight
import com.example.apptest4.ui.theme.YellowHighlight

class ChooseGameActivity : ComponentActivity() {
    private val bottomNavItems = listOf(
        NavbarItem(
            destination = Exercise,
            label = "Exercise",
            selectedIcon = R.drawable.gamepad_filled1,
            unselectedIcon = R.drawable.gamepad_unfilled,
        ),
        NavbarItem(
            destination = Stats,
            label = "Stats",
            selectedIcon = R.drawable.baseline_insert_chart_24,
            unselectedIcon = R.drawable.baseline_insert_chart_outlined_24,
        ),
        NavbarItem(
            destination = More,
            label = "More",
            selectedIcon = R.drawable.baseline_person_24,
            unselectedIcon = R.drawable.outline_person_24,
        ),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTest4Theme {
                val navController = rememberNavController()

                var selectedIndex by rememberSaveable {
                    mutableIntStateOf(0)
                }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavigationBar(modifier = Modifier.background(OrangeHighlight)) {
                            bottomNavItems.fastForEachIndexed { index, item ->
                                NavigationBarItem(
                                    selected = index == selectedIndex,
                                    onClick = {
                                        selectedIndex = index
                                        navController.navigate(item.destination)
                                    },
                                    icon = {
                                        Icon(
                                            tint = DarkGrey, painter = painterResource(
                                                id = if (selectedIndex == index)
                                                    item.selectedIcon else item.unselectedIcon
                                            ), contentDescription = item.label
                                        )
                                    },
                                    label = { Text(text = item.label, color = DarkGrey, style =
                                    MaterialTheme.typography.titleLarge) },
                                )
                            }
                        }
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize()
                    ) {
                        NavHost(navController = navController, startDestination = Exercise) {
                            composable<Exercise> { GamesFragment() }
                            composable<Stats> { Statistics() }
                            composable<More> { 
                                ProfileFragment(activity = this@ChooseGameActivity)
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun GamesFragment() {
        var reminders by rememberSaveable {
            mutableStateOf(false)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(60.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Choose exercise", style = MaterialTheme.typography.displayLarge, color = Orange,
            )
            Spacer(modifier = Modifier.height(30.dp))
            ImageButton(
                imageResId = R.drawable.zajac,
                onClick = { startActivity(
                        Intent(
                            this@ChooseGameActivity,
                            DicesChooseNumber::class.java
                        ))
                    }
            )
            Text(text = "Roll dices", style = MaterialTheme.typography.displaySmall, color = Orange,
            )
            ImageButton(
                imageResId = R.drawable.wyscigowka,
                onClick = { startActivity(
                    Intent(
                        this@ChooseGameActivity,
                        CatchTheBearTimeMode::class.java
                    )
                )}
            )
            Text(text = "Catch the bear", style = MaterialTheme.typography.displaySmall, color = Orange,
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 50.dp, vertical = 0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Reminders", style = MaterialTheme.typography.displaySmall, color = DarkOrange)
                Spacer(modifier = Modifier.weight(1f))
                Switch(checked = reminders, onCheckedChange = {
                        reminders = it
                })
            }
        }
    }
}
