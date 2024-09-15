package com.example.apptest4

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
                        NavigationBar {
                            bottomNavItems.fastForEachIndexed {index, item -> 
                                NavigationBarItem(
                                    selected = index == selectedIndex,
                                    onClick = { selectedIndex = index
                                              navController.navigate(item.destination)},
                                    icon = { Icon(painter = painterResource(id = if (selectedIndex == index)
                                    item.selectedIcon else item.unselectedIcon), contentDescription = item.label) },
                                    label = { Text(text = item.label) },)
                            }
                        }
                    }
                ) {
                    Box(modifier = Modifier
                        .padding(it)
                        .fillMaxSize()){
                        NavHost(navController = navController, startDestination = Exercise) {
                            composable<Exercise> { GamesFragment() }
                            composable<Stats> { Text(text = "Statistics") }
                            composable<More> { Text(text = "More") }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun GamesFragment() {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Hello")
            Button(onClick = {
                startActivity(
                    Intent(
                        this@ChooseGameActivity,
                        CatchTheBear::class.java
                    )
                )
            }) {
                Text(text = "Catch the bear")
            }
            Button(onClick = {
                startActivity(
                    Intent(
                        this@ChooseGameActivity,
                        DicesChooseNumber::class.java
                    )
                )
            }) {
                Text(text = "Roll dices")
            }
        }
    }
}
