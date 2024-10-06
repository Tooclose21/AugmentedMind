package com.example.apptest4.ui.userprofile

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ir.ehsannarmani.compose_charts.PieChart
import ir.ehsannarmani.compose_charts.models.Pie

@Composable
fun PieCharts(data: List<Double>, labels: List<String>, colors: List<Color>, modifier: Modifier = Modifier) {
    var pies = data
        .mapIndexed { index, it -> Pie(label = labels[index], data = it, color = colors[index % colors.size],
            selectedColor = colors[index % colors.size]) }

    PieChart(
        modifier = modifier,
        data = pies,
        onPieClick = {
            val pieIndex = pies.indexOf(it)
            pies = pies.mapIndexed { mapIndex, pie -> pie.copy(selected = pieIndex == mapIndex) }
        },
        selectedScale = 1.2f,
        scaleAnimEnterSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        style = Pie.Style.Stroke(width = 60.dp),
        colorAnimEnterSpec = tween(300),
        colorAnimExitSpec = tween(300),
        scaleAnimExitSpec = tween(300),
        spaceDegreeAnimExitSpec = tween(300),
        //style = Pie.Style.Fill
    )
}