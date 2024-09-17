package com.example.apptest4.helpers

import androidx.activity.ComponentActivity

data class NavbarItem(
    val destination: Any,
    val label: String,
    val selectedIcon: Int,
    val unselectedIcon: Int
)

data class ProfileItem(
    val navigate: (ComponentActivity) -> Unit,
    val label: String,
    val icon: Int,
)