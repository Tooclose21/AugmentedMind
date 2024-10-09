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
import androidx.lifecycle.LifecycleCoroutineScope
import com.example.apptest4.controllers.StatisticsController
import com.example.apptest4.controllers.StatisticsManager
import com.example.apptest4.helpers.memoryColorMap
import com.example.apptest4.helpers.memoryColorMapOrange
import com.example.apptest4.ui.theme.DarkGreen
import com.example.apptest4.ui.theme.DarkGray
import com.example.apptest4.ui.theme.DarkOrange
import com.example.apptest4.ui.theme.GreenHighlight
import com.example.apptest4.ui.theme.LightBack
import com.example.apptest4.ui.theme.Orange
import com.example.apptest4.ui.theme.Red
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar

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

    val options = listOf("General", "Memory", "Focus")

    var calendar by remember {
        mutableStateOf(Calendar.getInstance())
    }
    var lookUpMonth by remember {
        mutableIntStateOf(0)
    }
    val dicesNumberList by remember {
        mutableStateOf(listOf(2,3,4,5,6))
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
    if (ready){
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

        if (selectedIndex == 0) {}

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
                        style = MaterialTheme.typography.displaySmall, color = DarkGray)
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
                    style = MaterialTheme.typography.displayMedium,
                    color = DarkGreen
                )
            }
            item {
                ActivityChart(
                    activityList = manager.provideActivity(calendar)
                    , modifier = Modifier
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                )
            }
            item {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp), verticalAlignment = Alignment.CenterVertically) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = "Dices Number", style = MaterialTheme.typography.displaySmall, color = DarkGray)
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = {
                        if (dicesNumberIndex == 0) {
                            dicesNumberIndex = dicesNumberList.size - 1
                            return@IconButton
                        }
                        dicesNumberIndex -= 1
                        }
                    ) {
                        Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = "Left")
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = "${dicesNumberList[dicesNumberIndex]}",
                        style = MaterialTheme.typography.displaySmall, color = DarkGray)
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = {
                        if (dicesNumberIndex == dicesNumberList.size - 1) {
                            dicesNumberIndex = 0
                            return@IconButton
                        }
                        dicesNumberIndex +=1
                        }
                    ) {
                        Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "Right")
                    }

                }
            }
            item {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Mode", style = MaterialTheme.typography.displaySmall, color = DarkGray)
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = {
                        if (modesListIndex == 0) {
                            modesListIndex = modesList.size - 1
                            return@IconButton
                        }
                        modesListIndex -= 1
                    }
                    ) {
                        Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = "Left")
                    }
                    Spacer(modifier = Modifier.weight(0.5f))
                    Text(text = modesList[modesListIndex],
                        style = MaterialTheme.typography.displaySmall, color = DarkGray)
                    Spacer(modifier = Modifier.weight(0.5f))
                    IconButton(onClick = {
                        if (modesListIndex == modesList.size - 1) {
                            modesListIndex = 0
                            return@IconButton
                        }
                        modesListIndex +=1
                    }
                    ) {
                        Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "Right")
                    }

                }
            }
        }

        if (selectedIndex == 2) {
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
                        style = MaterialTheme.typography.displaySmall, color = DarkGray)
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
                    style = MaterialTheme.typography.displayMedium,
                    color = Orange
                )
            }
            item {
                ActivityChart(
                    activityList = manager.provideActivityBear(calendar)
                    , modifier = Modifier
                        .padding(horizontal = 25.dp)
                        .fillMaxWidth(),
                    memoryColorMapOrange
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
            item { FocusStatistics() }
            item{
                Column (modifier = Modifier
                    .fillMaxSize()
                    .padding(25.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)){
                    Text(
                        text = "Current speed:",
                        style = MaterialTheme.typography.displaySmall,
                        color = DarkOrange
                    )
                    Text(
                        text = "Speed last month:",
                        style = MaterialTheme.typography.displaySmall,
                        color = DarkGray
                    )
                    Text(
                        text = "Three months ago:",
                        style = MaterialTheme.typography.displaySmall,
                        color = DarkGray
                    )
                }
            }
        }
    }
    }else {
        Column (modifier = Modifier
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center  ){
            Text(
                text = "Loading statistics", style = MaterialTheme.typography.displayMedium,
                color = DarkGray
            )
        }
    }
}

@Composable
fun MemoryStatistics(modifier: Modifier = Modifier) {
    PieCharts(
        data = listOf(1.0, 4.5), labels = listOf("One", "Four"), colors = listOf(
            Red, GreenHighlight
        ), modifier = modifier
    )
}
@Composable
fun FocusStatistics(modifier: Modifier = Modifier) {
    LineCharts(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
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
fun ActivityChartRow(activityList: List<Int>, modifier: Modifier = Modifier, memoryColorMap: Map<Int, Color>) {
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
fun ActivityChart(activityList: List<Int>, modifier: Modifier = Modifier, memoryColorMap: Map<Int, Color>) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(3.dp)) {
        ActivityChartLabel()
        val rowsNumber = activityList.size / 7
        for (i in 0..<rowsNumber) {
            ActivityChartRow(
                activityList = activityList.subList(
                    fromIndex = i * 7,
                    toIndex = i * 7 + 7
                ), memoryColorMap = memoryColorMap
            )
        }
    }
}
