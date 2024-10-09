package com.example.apptest4.ui.userprofile

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.example.apptest4.ui.theme.DarkOrange
import com.example.apptest4.ui.theme.Orange
import com.example.apptest4.ui.theme.OrangeHighlight
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.AnimationMode
import ir.ehsannarmani.compose_charts.models.DotProperties
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.Line
import ir.ehsannarmani.compose_charts.models.StrokeStyle

@Composable
fun LineCharts(modifier: Modifier) {
    LineChart(
        modifier = modifier,
        data = listOf(
            Line(
                label = "Speed per item",
                values = listOf(28.0,41.0,5.0,10.0,35.0),
                color = SolidColor(Orange),
                drawStyle = DrawStyle.Stroke(
                    width = 3.dp,
                    strokeStyle = StrokeStyle.Dashed(intervals = floatArrayOf(10f,10f), phase = 15f)
                ),
                firstGradientFillColor = OrangeHighlight.copy(alpha = .5f),
                secondGradientFillColor = Color.Transparent,
                strokeAnimationSpec = tween(2000, easing = EaseInOutCubic),
                gradientAnimationDelay = 1000,
                dotProperties = DotProperties(
                    enabled = true,
                    color = SolidColor(Color.White),
                    strokeWidth = 4.dp,
                    radius = 4.dp,
                    strokeColor = SolidColor(Orange),
                )
            )
        ),
        animationMode = AnimationMode.Together(delayBuilder = {
            it * 500L
        }),
    )
}