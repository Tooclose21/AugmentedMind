package com.example.apptest4.ui.userprofile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import com.example.apptest4.helpers.createNotification
import com.example.apptest4.ui.gamehelpers.ChooseGameActivity
import com.example.apptest4.ui.theme.AppTest4Theme
import com.example.apptest4.ui.theme.DarkGreen
import com.example.apptest4.ui.theme.GreenHighlight
import com.example.apptest4.ui.theme.LightBack
import java.util.Calendar
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.material3.TimePicker


class RemindersActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTest4Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize()
                    ) {
                        ManageReminders()
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ManageReminders() {
        val checkedList = remember { mutableStateListOf<Int>() }
        val options = listOf("M", "T", "W", "T", "F", "S", "S")
        var hour by remember {
            mutableStateOf(0)
        }
        val timePickerState = rememberTimePickerState()
        var showTimePicker by remember {
            mutableStateOf(false)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp, vertical = 280.dp),
            verticalArrangement = Arrangement.spacedBy(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MultiChoiceSegmentedButtonRow {
                options.forEachIndexed { index, label ->
                    SegmentedButton(
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = options.size
                        ),
                        onCheckedChange = {
                            if (index in checkedList) {
                                checkedList.remove(index)
                            } else {
                                checkedList.add(index)
                            }
                        },

                        icon = {},
                        colors = SegmentedButtonDefaults.colors().copy(
                            activeContainerColor = GreenHighlight,
                            inactiveContainerColor = LightBack
                        ),
                        checked = index in checkedList
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
            Spacer(modifier = Modifier.weight(0.9f))
            Button(colors = ButtonDefaults.buttonColors(containerColor = GreenHighlight),
                onClick = {
                    showTimePicker = true
                }) {
                Text(text = "Set time", style = MaterialTheme.typography.displayMedium)
            }
            Button(colors = ButtonDefaults.buttonColors(containerColor = DarkGreen),
                onClick = {
                    createNotification(
                        this@RemindersActivity,
                        CalcutlateFirstNotifi(checkedList.map { it + 1 }, hour),
                        0,
                        checkedList.map { it + 1 },
                        hour
                    )
                    Toast.makeText(this@RemindersActivity, "Notifications added", Toast.LENGTH_SHORT).show()
                    finish()
                }) {
                Text(text = "Set reminders", style = MaterialTheme.typography.displayMedium)
            }
        }
        if (showTimePicker) {
            TimePickerDialog(
                onDismissRequest = { },
                confirmButton = {
                    Button(onClick = { showTimePicker = false
                        hour = timePickerState.hour*60 + timePickerState.minute
                    }) {
                        Text(text = "Ok", style = MaterialTheme.typography.titleLarge)
                    }
                },
                dismissButton = { Button(onClick = { showTimePicker = false }) { Text(text = "Cancel", style = MaterialTheme.typography.titleLarge) } }
            )
            {
                TimePicker(state = timePickerState)
            }

        }

    }
    @Composable
    fun TimePickerDialog(
        title: String = "Select Time",
        onDismissRequest: () -> Unit,
        confirmButton: @Composable (() -> Unit),
        dismissButton: @Composable (() -> Unit)? = null,
        containerColor: Color = MaterialTheme.colorScheme.surface,
        content: @Composable () -> Unit,
    ) {
        Dialog(
            onDismissRequest = onDismissRequest,
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            ),
        ) {
            Surface(
                shape = MaterialTheme.shapes.extraLarge,
                tonalElevation = 6.dp,
                modifier = Modifier
                    .width(IntrinsicSize.Min)
                    .height(IntrinsicSize.Min)
                    .background(
                        shape = MaterialTheme.shapes.extraLarge,
                        color = containerColor
                    ),
                color = containerColor
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp),
                        text = title,
                        style = MaterialTheme.typography.displayMedium
                    )
                    content()
                    Row(
                        modifier = Modifier
                            .height(40.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        confirmButton()
                        Spacer(modifier = Modifier.weight(2f))
                        dismissButton?.invoke()
                    }
                }
            }
        }
    }
    private fun CalcutlateFirstNotifi(week: List<Int>, hour:Int): Long {
        val calendar = Calendar.getInstance()
        while (!week.contains(calendar.get(Calendar.DAY_OF_WEEK))) {
            calendar.add(Calendar.DATE, 1)
        }
        calendar.set(Calendar.HOUR, hour/60)
        calendar.set(Calendar.MINUTE, hour%60)
        return calendar.timeInMillis
    }
}


