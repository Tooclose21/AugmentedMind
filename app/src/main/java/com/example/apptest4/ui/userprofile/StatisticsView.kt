package com.example.apptest4.ui.userprofile

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import com.example.apptest4.controllers.StatisticsController
import com.example.apptest4.controllers.StatisticsManager
import com.example.apptest4.helpers.memoryColorMap
import com.example.apptest4.ui.theme.DarkGreen
import com.example.apptest4.ui.theme.DarkGray
import com.example.apptest4.ui.theme.DarkOrange
import com.example.apptest4.ui.theme.GreenHighlight
import com.example.apptest4.ui.theme.LightBack
import com.example.apptest4.ui.theme.Orange
import com.example.apptest4.ui.theme.Red
import ir.ehsannarmani.compose_charts.extensions.format
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import kotlin.math.sign

private val manager = StatisticsManager(StatisticsController())

private fun CoroutineScope.fetch(onFetch: (Boolean) -> Unit) = launch {
    manager.fetchAll(onFetch)
}

@Composable
fun StatisticsView(lifecycleScope: LifecycleCoroutineScope) {

    var selectedIndex by remember { mutableStateOf(0) }
    var ready by remember {
        mutableStateOf(false)
    }
    lifecycleScope.fetch { ready = it }

    val options = listOf("Memory", "Focus")

    var calendar by remember {
        mutableStateOf(Calendar.getInstance())
    }
    var lookUpMonth by remember {
        mutableIntStateOf(0)
    }
    val dicesNumberList by remember {
        mutableStateOf(listOf(2, 3, 4, 5, 6))
    }
    var dicesNumberIndex by remember {
        mutableStateOf(0)
    }
    val modesList by remember {
        mutableStateOf(listOf("Slow", "Medium", "Fast"))
    }
    var modesListIndex by remember {
        mutableStateOf(0)
    }
    var timeList by remember {
        mutableStateOf(listOf("3 min", "5 min", "10 min"))
    }
    var timeListIndex by remember {
        mutableStateOf(0)
    }
    if (ready) {
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
                                index = index, count = options.size
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

            if (selectedIndex == 0) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {
                            lookUpMonth -= 1
                            calendar = Calendar.getInstance().apply {
                                add(Calendar.MONTH, lookUpMonth)
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowLeft,
                                contentDescription = "Left"
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "${SimpleDateFormat("MMM").format(calendar.time)} ${
                                calendar.get(
                                    Calendar.YEAR
                                )
                            }", style = MaterialTheme.typography.displaySmall, color = DarkGray
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(onClick = {
                            lookUpMonth += 1
                            calendar = Calendar.getInstance().apply {
                                add(Calendar.MONTH, lookUpMonth)
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                contentDescription = "Right"
                            )
                        }

                    }
                }
                item {
                    Text(
                        text = "Activity chart",
                        style = MaterialTheme.typography.displayMedium,
                        color = DarkGreen
                    )
                }
                item {
                    ActivityChart(
                        activityList = manager.provideActivity(calendar),
                        modifier = Modifier
                            .padding(horizontal = 25.dp)
                            .fillMaxWidth(),
                        memoryColorMap
                    )
                }
                item { Spacer(modifier = Modifier.height(10.dp)) }
                item {
                    Text(
                        text = "Accuracy",
                        style = MaterialTheme.typography.displayMedium,
                        color = DarkGreen
                    )
                }
                item {
                    MemoryStatistics(
                        data = manager.provideMemoryStatistics(
                            calendar, dicesNumberList[dicesNumberIndex], modesList[modesListIndex]
                        ), modifier = Modifier
                            .fillMaxWidth()
                            .height(260.dp)
                    )
                }
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 40.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = "Dices Number",
                            style = MaterialTheme.typography.displaySmall,
                            color = DarkGray
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(onClick = {
                            if (dicesNumberIndex == 0) {
                                dicesNumberIndex = dicesNumberList.size - 1
                                return@IconButton
                            }
                            dicesNumberIndex -= 1
                        }) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowLeft,
                                contentDescription = "Left"
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "${dicesNumberList[dicesNumberIndex]}",
                            style = MaterialTheme.typography.displaySmall,
                            color = DarkGray
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(onClick = {
                            if (dicesNumberIndex == dicesNumberList.size - 1) {
                                dicesNumberIndex = 0
                                return@IconButton
                            }
                            dicesNumberIndex += 1
                        }) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                contentDescription = "Right"
                            )
                        }

                    }
                }
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 40.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Mode",
                            style = MaterialTheme.typography.displaySmall,
                            color = DarkGray
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(onClick = {
                            if (modesListIndex == 0) {
                                modesListIndex = modesList.size - 1
                                return@IconButton
                            }
                            modesListIndex -= 1
                        }) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowLeft,
                                contentDescription = "Left"
                            )
                        }
                        Spacer(modifier = Modifier.weight(0.5f))
                        Text(
                            text = modesList[modesListIndex],
                            style = MaterialTheme.typography.displaySmall,
                            color = DarkGray
                        )
                        Spacer(modifier = Modifier.weight(0.5f))
                        IconButton(onClick = {
                            if (modesListIndex == modesList.size - 1) {
                                modesListIndex = 0
                                return@IconButton
                            }
                            modesListIndex += 1
                        }) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                contentDescription = "Right"
                            )
                        }

                    }
                }
            }

            if (selectedIndex == 1) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {
                            lookUpMonth -= 1
                            calendar = Calendar.getInstance().apply {
                                add(Calendar.MONTH, lookUpMonth)
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowLeft,
                                contentDescription = "Left"
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "${SimpleDateFormat("MMM").format(calendar.time)} ${
                                calendar.get(
                                    Calendar.YEAR
                                )
                            }", style = MaterialTheme.typography.displaySmall, color = DarkGray
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(onClick = {
                            lookUpMonth += 1
                            calendar = Calendar.getInstance().apply {
                                add(Calendar.MONTH, lookUpMonth)
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                contentDescription = "Right"
                            )
                        }

                    }
                }
                item {
                    Text(
                        text = "Activity chart",
                        style = MaterialTheme.typography.displayMedium,
                        color = Orange
                    )
                }
                item {
                    ActivityChart(
                        activityList = manager.provideActivityBear(calendar),
                        modifier = Modifier
                            .padding(horizontal = 25.dp)
                            .fillMaxWidth(),
                        memoryColorMap
                    )
                }
                item { Spacer(modifier = Modifier.height(10.dp)) }

                item {
                    Text(
                        text = "Speed chart",
                        style = MaterialTheme.typography.displayMedium,
                        color = Orange
                    )
                }
                item {
                    FocusStatistics(
                        manager.provideFocusStatistics(
                            calendar, timeList[timeListIndex]
                        )
                    )
                }
                item { Spacer(modifier = Modifier.height(10.dp)) }
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 80.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {
                            if (timeListIndex == 0) {
                                timeListIndex = timeList.size - 1
                                return@IconButton
                            }
                            timeListIndex -= 1
                        }) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowLeft,
                                contentDescription = "Left"
                            )
                        }
                        Spacer(modifier = Modifier.weight(0.5f))
                        Text(
                            text = timeList[timeListIndex],
                            style = MaterialTheme.typography.displaySmall,
                            color = DarkGray
                        )
                        Spacer(modifier = Modifier.weight(0.5f))
                        IconButton(onClick = {
                            if (timeListIndex == timeList.size - 1) {
                                timeListIndex = 0
                                return@IconButton
                            }
                            timeListIndex += 1
                        }) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                contentDescription = "Right"
                            )
                        }

                    }
                }
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 25.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {

                        val provideAverageSpeedMonth =
                            manager.provideAverageSpeedMonth(calendar, timeList[timeListIndex])
                        Text(
                            text = if (provideAverageSpeedMonth >= 0) "Current speed: ${(provideAverageSpeedMonth/1000).format(2)} s" else "Current speed: Not available",
                            style = MaterialTheme.typography.displaySmall,
                            color = DarkOrange
                        )
                        val provideAverageSpeedLM =
                            manager.provideAverageSpeedLM(calendar, timeList[timeListIndex])
                        Text(
                            text = if (provideAverageSpeedLM >= 0) "Last month: ${(provideAverageSpeedLM/1000).format(2)} s" else "Last month: Not available",
                            style = MaterialTheme.typography.displaySmall,
                            color = DarkGray
                        )
                        val provideAverageSpeed3LM =
                            manager.provideAverageSpeed3LM(calendar, timeList[timeListIndex])
                        Text(
                            text = if (provideAverageSpeed3LM >= 0) "Last 3 months: ${(provideAverageSpeed3LM/1000).format(2)} s" else "Last 3 months: Not available",
                            style = MaterialTheme.typography.displaySmall,
                            color = DarkGray
                        )
                    }
                }
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Loading statistics",
                style = MaterialTheme.typography.displayMedium,
                color = DarkGray
            )
        }
    }
}

@Composable
fun MemoryStatistics(modifier: Modifier = Modifier, data: Map<String, Int>) {
    if (data.values.all { it == 0 }) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "You haven't played yet",
                style = MaterialTheme.typography.displayMedium,
                color = DarkGray
            )
        }
    } else {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            PieCharts(
                data = data, modifier = modifier
            )
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                Modifier.fillMaxWidth(),

                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                val colors = mapOf(
                    Pair("Wrong", Red), Pair("Correct", GreenHighlight)
                )
                Row(
                    modifier = Modifier
                        .height(18.dp)
                        .width(18.dp)
                        .clip(CircleShape)
                        .background(colors["Correct"]!!)
                ) {
                    Text(text = "")
                }
                Spacer(modifier = Modifier.width(9.dp))
                val percent = (data["Correct"]!!.toDouble() / data.values.sum()) * 100
                Text(
                    text = "${percent.toInt()}% correct",
                    style = MaterialTheme.typography.displaySmall,
                    fontSize = 35.sp,
                    color = DarkGray
                )
                Spacer(modifier = Modifier.width(20.dp))
                Row(
                    modifier = Modifier
                        .height(18.dp)
                        .width(18.dp)
                        .clip(CircleShape)
                        .background(colors["Wrong"]!!)
                ) {
                    Text(text = "")
                }
                Spacer(modifier = Modifier.width(9.dp))
                val percentW = 100 - percent.toInt()
                Text(
                    text = "${percentW}% wrong",
                    style = MaterialTheme.typography.displaySmall,
                    fontSize = 35.sp,
                    color = DarkGray
                )
            }
        }
    }
}

@Composable
fun FocusStatistics(data: MutableMap<Long, Double>, modifier: Modifier = Modifier) {
    if (data.values.isEmpty()) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "You haven't played yet",
                style = MaterialTheme.typography.displaySmall,
                fontSize = 35.sp,
                color = DarkGray
            )
        }
    } else if (data.values.size < 2) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(horizontal = 80.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Chart available after two days of exercising",
                style = MaterialTheme.typography.displaySmall,
                fontSize = 35.sp,
                color = DarkGray,
                softWrap = true,
                textAlign = TextAlign.Center
            )
        }
    } else {
        LineCharts(data = data.values.map { it / 1000 }, labels = data.keys.map {
            Calendar.getInstance().apply { timeInMillis = it }.get(Calendar.DAY_OF_MONTH).toString()
        }, modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
        )
    }
}

@Composable
fun ActivityChartLabel(modifier: Modifier = Modifier) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(3.dp)) {
        Row(
            modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "M")
        }
        Row(
            modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "T")
        }
        Row(
            modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "W")
        }
        Row(
            modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "T")
        }
        Row(
            modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "F")
        }
        Row(
            modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "S")
        }
        Row(
            modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "S")
        }
    }
}

@Composable
fun ActivityChartRow(
    activityList: List<Int>, modifier: Modifier = Modifier, memoryColorMap: Map<Int, Color>
) {
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
fun ActivityChart(
    activityList: List<Int>, modifier: Modifier = Modifier, memoryColorMap: Map<Int, Color>
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(3.dp)) {
        ActivityChartLabel()
        val rowsNumber = activityList.size / 7
        for (i in 0..<rowsNumber) {
            ActivityChartRow(
                activityList = activityList.subList(
                    fromIndex = i * 7, toIndex = i * 7 + 7
                ), memoryColorMap = memoryColorMap
            )
        }
    }
}
