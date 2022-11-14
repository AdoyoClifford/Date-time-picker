package com.adoyo.timeanddatepicker

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adoyo.timeanddatepicker.ui.theme.TimeAndDatePickerTheme
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimeAndDatePickerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    var pickedDate by remember {
                        mutableStateOf(LocalDate.now())
                    }
                    var pickedTime by remember {
                        mutableStateOf(LocalTime.NOON)
                    }

                    val formattedDate by remember {
                        derivedStateOf {
                            DateTimeFormatter.ofPattern("MMM dd yyyy").format(pickedDate)
                        }
                    }

                    val formattedTime by remember {
                        derivedStateOf {
                            DateTimeFormatter.ofPattern("hh:mm").format(pickedTime)
                        }
                    }

                    val dateDialogState = rememberMaterialDialogState()
                    val timeDialogState = rememberMaterialDialogState()

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(onClick = {
                            dateDialogState.show()
                        }) {
                            Text(text = "pick date")
                        }
                        Text(text = formattedDate)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = {
                            timeDialogState.show()
                        }) {
                            Text(text = "Pick time")
                        }
                        Text(text = formattedTime)
                    }
                    MaterialDialog(dialogState = dateDialogState, buttons = {
                        positiveButton(text = "ok") {
                            Toast.makeText(applicationContext, "Clicked Ok", Toast.LENGTH_LONG)
                                .show()
                        }
                        negativeButton(text = "cancel")
                    }
                    ) {
                        datepicker(
                            initialDate = LocalDate.now(),
                            title = "pick a date",
                            allowedDateValidator = {
                                it.dayOfMonth % 2 == 1
                            }
                        ) {
                            pickedDate = it
                        }
                    }

                    MaterialDialog(dialogState = timeDialogState, buttons = {
                        positiveButton(text = "ok") {
                            Toast.makeText(applicationContext, "Clicked Ok", Toast.LENGTH_LONG)
                                .show()
                        }
                        negativeButton(text = "cancel")
                    }
                    ) {
                        timepicker(
                            initialTime = LocalTime.NOON,
                            title = "pick a time",
                            timeRange = LocalTime.MIDNIGHT..LocalTime.NOON
                        ) {
                            pickedTime = it
                        }
                    }

                }
            }
        }
    }
}


