package com.pasichdev.pharmate.presentation.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.pasichdev.pharmate.R
import com.pasichdev.pharmate.domain.model.MedicationDoseInfo
import com.pasichdev.pharmate.presentation.components.QuantityInputField
import com.pasichdev.pharmate.utils.formattedTime

enum class SetTimeAndDosePlanningAction {
    CLOSE, SAVE
}

//TODO Перевірити полля вводу перед віправкою
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetTimeAndDosePlanning(
    measurementUnit: String, action: (SetTimeAndDosePlanningAction, MedicationDoseInfo?) -> Unit
) {
    val timeState = rememberTimePickerState(
        is24Hour = true
    )
    var timeSelected by remember { mutableStateOf("") }
    var doseValue by remember { mutableStateOf("1") }


    Dialog(
        onDismissRequest = { action(SetTimeAndDosePlanningAction.CLOSE, null) },
        properties = DialogProperties(usePlatformDefaultWidth = true)
    ) {
        ElevatedCard(
            modifier = Modifier.background(
                color = MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.extraLarge
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            shape = MaterialTheme.shapes.extraLarge
        ) {

            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.enterTimeAndDosageDialog),
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.height(20.dp))
                TimeInput(
                    state = timeState
                )
                QuantityInputField(
                    inputName = stringResource(R.string.dosage),
                    value = doseValue,
                    onValueChange = { doseValue = it },
                    unit = measurementUnit
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End
                ) {
                    Button(modifier = Modifier.padding(end = 8.dp),
                        onClick = { action(SetTimeAndDosePlanningAction.CLOSE, null) }) {
                        Text(
                            text = stringResource(R.string.cancel)
                        )
                    }
                    Button(modifier = Modifier.padding(start = 8.dp), onClick = {
                        timeSelected = formattedTime(timeState.hour, timeState.minute)
                        action(
                            SetTimeAndDosePlanningAction.SAVE, MedicationDoseInfo(
                                time = timeSelected, dose = doseValue.toIntOrNull() ?: 1
                            )
                        )
                    }) {
                        Text(text = stringResource(R.string.add))
                    }
                }
            }
        }
    }
}