package com.example.apptest4.ui.userprofile

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.apptest4.ui.theme.GreenHighlight
import com.example.apptest4.ui.theme.Red
import ir.ehsannarmani.compose_charts.PieChart
import ir.ehsannarmani.compose_charts.models.Pie

@Composable
fun PieCharts(data: Map<String, Int>, modifier: Modifier = Modifier) {
    val colors = mapOf(
    Pair("Wrong", Red), Pair("Correct", GreenHighlight)
    )
    var pies = data.map { (k, v) -> Pie(label = k, data = v.toDouble(), color = colors[k]!!, selectedColor = colors[k]!!)}

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