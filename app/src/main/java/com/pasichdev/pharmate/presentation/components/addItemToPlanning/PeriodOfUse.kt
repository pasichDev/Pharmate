package com.pasichdev.pharmate.presentation.components.addItemToPlanning

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pasichdev.pharmate.R
import com.pasichdev.pharmate.defaultDateFormat
import com.pasichdev.pharmate.presentation.components.LabeledValueItem
import com.pasichdev.pharmate.presentation.components.PreviewChip
import com.pasichdev.pharmate.ui.theme.titlePlanningCard
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

enum class ShowDialogsDatePicker {
    START_DATE, END_DATE, NONE
}

enum class PeriodOfUseScreenAction {
    SAVE_START_DATE, SAVE_END_DATE
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeriodOfUse(
    modifier: Modifier = Modifier,
    startDateUse: String,
    endDateUse: String,
    fullVisible: Boolean,
    activateCard: (ShowCategorySettingsTime) -> Unit,
    cardAction: (PeriodOfUseScreenAction, String) -> Unit
) {
    var showDialog by remember { mutableStateOf(ShowDialogsDatePicker.NONE) }

    val startDateState =
        rememberDatePickerState(initialSelectedDateMillis = convertStringToDateMillis(startDateUse))

    val startDateSelected = startDateState.selectedDateMillis?.let {
        Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
    }?.plusDays(1)
    val endDateState = rememberDatePickerState(
        initialSelectedDateMillis = convertStringToDateMillis(endDateUse),
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val selectedDate =
                    Instant.ofEpochMilli(utcTimeMillis).atZone(ZoneId.systemDefault()).toLocalDate()
                return startDateSelected?.let { !selectedDate.isBefore(it) } != false
            }
        })
    val selectedDateState =
        if (showDialog == ShowDialogsDatePicker.START_DATE) startDateState else if (showDialog == ShowDialogsDatePicker.END_DATE) endDateState else rememberDatePickerState()


    Card(modifier = modifier.fillMaxWidth(),
        onClick = { activateCard(ShowCategorySettingsTime.PERIOD) }) {
        Column(modifier = Modifier.padding(top = 20.dp, start = 20.dp, end = 20.dp)) {
            Text(stringResource(R.string.periodUsePill), style = titlePlanningCard)

            if (!fullVisible) {
                PreviewChip(
                    textTrue = stringResource(R.string.noSett),
                    textFalse = "$startDateUse / $endDateUse",
                    isPreview = startDateUse.isEmpty() || endDateUse.isEmpty()
                ) {
                    activateCard(ShowCategorySettingsTime.PERIOD)
                }

            }
        }

        if (fullVisible) {
            Spacer(modifier = Modifier.height(10.dp))

            LabeledValueItem (
                itemTitle = stringResource(R.string.startUse), value = checkValue(startDateUse)
            ) {
                showDialog = ShowDialogsDatePicker.START_DATE
            }
            Spacer(modifier = Modifier.height(10.dp))
            LabeledValueItem (
                itemTitle = stringResource(R.string.endUse),
                value = checkValue(endDateUse),
                enabled = startDateUse.isNotEmpty()
            ) { showDialog = ShowDialogsDatePicker.END_DATE }


        }
        Spacer(modifier = Modifier.height(20.dp))
    }

    if (showDialog != ShowDialogsDatePicker.NONE) {
        DatePickerDialogComponent(onDismiss = { showDialog = ShowDialogsDatePicker.NONE },
            dateState = selectedDateState,
            onConfirm = { selectedDateMillis ->
                val selectedDate = selectedDateMillis?.let {
                    Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
                }
                if (selectedDate != null) {
                    var date = selectedDate.format(defaultDateFormat)

                    when (showDialog) {
                        ShowDialogsDatePicker.START_DATE -> {
                            cardAction(
                                PeriodOfUseScreenAction.SAVE_START_DATE, date
                            )
                        }

                        ShowDialogsDatePicker.END_DATE -> {
                            cardAction(
                                PeriodOfUseScreenAction.SAVE_END_DATE, date
                            )
                        }

                        /**
                         * TODO Опрацювання помилки якщо дата не коректна
                         */

                        ShowDialogsDatePicker.NONE -> Unit
                    }
                }



                showDialog = ShowDialogsDatePicker.NONE
            })
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialogComponent(
    onDismiss: () -> Unit,
    onConfirm: (Long?) -> Unit,
    dateState: DatePickerState = rememberDatePickerState()
) {

    DatePickerDialog(onDismissRequest = onDismiss, confirmButton = {
        Button(onClick = {
            onConfirm(dateState.selectedDateMillis)
        }) {
            Text(text = stringResource(R.string.save))
        }
    }, dismissButton = {
        Button(onClick = onDismiss) {
            Text(text = stringResource(R.string.cancel))
        }
    }) {
        DatePicker(state = dateState, showModeToggle = true)
    }

}

@Composable
fun checkValue(value: String): String {
    return if (value.isNotEmpty()) value else stringResource(R.string.noSett)
}


fun convertStringToDateMillis(value: String): Long? {
    return try {
        LocalDate.parse(value, defaultDateFormat).atStartOfDay(ZoneId.systemDefault()).toInstant()
            .toEpochMilli()
    } catch (_: Exception) {
        null
    }

}