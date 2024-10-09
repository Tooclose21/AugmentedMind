package com.example.apptest4.helpers

import androidx.compose.ui.graphics.Color
import com.example.apptest4.ui.theme.Gray
import com.example.apptest4.ui.theme.GreenHighlight
import com.example.apptest4.ui.theme.LightBack
import com.example.apptest4.ui.theme.Orange
import com.example.apptest4.ui.theme.OrangeHighlight

val memoryColorMap = mapOf(
    Pair(0, Gray),
    Pair(1, GreenHighlight),
    Pair(-1, LightBack)
)

val memoryColorMapOrange = mapOf(
    Pair(0, Gray),
    Pair(1, OrangeHighlight),
    Pair(-1, LightBack)
)
