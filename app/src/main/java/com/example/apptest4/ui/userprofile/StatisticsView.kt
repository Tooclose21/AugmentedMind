package com.example.apptest4.ui.userprofile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apptest4.helpers.memoryColorMap
import com.example.apptest4.ui.theme.DarkGreen
import com.example.apptest4.ui.theme.DarkGrey
import com.example.apptest4.ui.theme.GreenHighlight
import com.example.apptest4.ui.theme.LightBack
import com.example.apptest4.ui.theme.Orange
import com.example.apptest4.ui.theme.OrangeHighlight
import java.text.SimpleDateFormat
import java.util.Calendar

@Composable
fun StatisticsView() {
    var selectedIndex by remember { mutableStateOf(0) }
    val options = listOf("General", "Memory", "Focus")

    var calendar by remember {
        mutableStateOf(Calendar.getInstance())
    }
    var lookUpMonth by remember {
        mutableIntStateOf(0)
    }
    LazyColumn(
        modifier = Modifier
            // .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            SingleChoiceSegmentedButtonRow {
                options.forEachIndexed { index, label ->
                    SegmentedButton(
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = options.size
                        ),
                        onClick = { selectedIndex = index },
                        selected = index == selectedIndex,
                        icon = {},
                        colors = SegmentedButtonDefaults.colors().copy(
                            activeContainerColor = GreenHighlight,
                            inactiveContainerColor = LightBack
                        )

                    ) {
                        Text(
                            label,
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 28.sp,
                            color = Color.DarkGray
                        )
                    }
                }
            }
        }
        if (selectedIndex == 1) {
            item {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = {
                        lookUpMonth -= 1
                        calendar = Calendar.getInstance().apply {
                            add(Calendar.MONTH, lookUpMonth)
                        }
                    }) {
                        Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = "Left")
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = "${SimpleDateFormat("MMM").format(calendar.time)} ${calendar.get(Calendar.YEAR)}",
                        style = MaterialTheme.typography.displaySmall, color = DarkGrey)
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = {
                        lookUpMonth += 1
                        calendar = Calendar.getInstance().apply {
                            add(Calendar.MONTH, lookUpMonth)
                        }
                    }) {
                        Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "Right")
                    }

                }
            }
            item {
                Text(
                    text = "Activity chart",
                    style = MaterialTheme.typography.displaySmall,
                    color = DarkGreen
                )
            }
            item {
                ActivityChart(
                    activityList = listOf(
                        -1,
                        -1,
                        0,
                        1,
                        1,
                        0,
                        0,
                        1,
                        0,
                        0,
                        1,
                        1,
                        0,
                        0,
                        1,
                        0,
                        0,
                        1,
                        1,
                        0,
                        0,
                        1,
                        0,
                        0,
                        1,
                        1,
                        0,
                        0,
                        1,
                        0,
                        -1,
                        -1,
                        -1,
                        -1,
                        -1
                    ), modifier = Modifier
                        .padding(horizontal = 35.dp)
                        .fillMaxWidth()
                )
            }
            item { Spacer(modifier = Modifier.height(10.dp)) }
            item {
                Text(
                    text = "Accuracy",
                    style = MaterialTheme.typography.displaySmall,
                    color = DarkGreen
                )
            }
            item {
                MemoryStatistics(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }
        }
    }
}

@Composable
fun MemoryStatistics(modifier: Modifier = Modifier) {
    PieCharts(
        data = listOf(1.0, 4.5), labels = listOf("One", "Four"), colors = listOf(
            Orange, GreenHighlight
        ), modifier = modifier
    )
}

@Composable
fun ActivityChartLabel(modifier: Modifier = Modifier) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(3.dp)) {
        Row(
            modifier = Modifier
                .weight(1f),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "M")
        }
        Row(
            modifier = Modifier
                .weight(1f),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "T")
        }
        Row(
            modifier = Modifier
                .weight(1f),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "W")
        }
        Row(
            modifier = Modifier
                .weight(1f),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "T")
        }
        Row(
            modifier = Modifier
                .weight(1f),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "F")
        }
        Row(
            modifier = Modifier
                .weight(1f),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "S")
        }
        Row(
            modifier = Modifier
                .weight(1f),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "S")
        }
    }
}

@Composable
fun ActivityChartRow(activityList: List<Int>, modifier: Modifier = Modifier) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(3.dp)) {
        Row(
            modifier = Modifier
                .weight(1f)
                .background(memoryColorMap[activityList[0]] ?: Color.Gray)
        ) {
            Text(text = "")
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .background(memoryColorMap[activityList[1]] ?: Color.Gray)
        ) {
            Text(text = "")
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .background(memoryColorMap[activityList[2]] ?: Color.Gray)
        ) {
            Text(text = "")
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .background(memoryColorMap[activityList[3]] ?: Color.Gray)
        ) {
            Text(text = "")
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .background(memoryColorMap[activityList[4]] ?: Color.Gray)
        ) {
            Text(text = "")
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .background(memoryColorMap[activityList[5]] ?: Color.Gray)
        ) {
            Text(text = "")
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .background(memoryColorMap[activityList[6]] ?: Color.Gray)
        ) {
            Text(text = "")
        }
    }
}

@Composable
fun ActivityChart(activityList: List<Int>, modifier: Modifier = Modifier) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(3.dp)) {
        ActivityChartLabel()
        val rowsNumber = activityList.size / 7
        for (i in 0..<rowsNumber) {
            ActivityChartRow(
                activityList = activityList.subList(
                    fromIndex = i * 7,
                    toIndex = i * 7 + 7
                )
            )
        }
    }
}
