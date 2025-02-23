package com.pasichdev.pharmate.presentation.components.addItemToPlanning

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pasichdev.pharmate.R
import com.pasichdev.pharmate.presentation.dialog.SetTimeAndDosePlanning
import com.pasichdev.pharmate.presentation.dialog.SetTimeAndDosePlanningAction
import com.pasichdev.pharmate.presentation.viewmodel.AddItemPlanningViewModel
import com.pasichdev.pharmate.presentation.viewmodel.ItemPlanningEvent.*


enum class ShowCategorySettingsTime {
    IF_NECESSARY, DAYS, WEEKLY, PERIOD
}


@Composable
fun EditDosageInformation(
    modifier: Modifier = Modifier,
    addItemPlanningViewModel: AddItemPlanningViewModel = hiltViewModel()

) {
    val state by addItemPlanningViewModel.state.collectAsStateWithLifecycle()
    var showCategorySettingsTime by remember { mutableStateOf(ShowCategorySettingsTime.IF_NECESSARY) }
    var showDialogSetTimeAndDosePlanning by remember { mutableStateOf(false) }


    Column {
        MedicationAsNeeded(modifier = modifier, state.selectedIfMedicationIsAsNeeded, onSelect = {
            addItemPlanningViewModel.onEvent(
                UpdateSelectedIfMedicationIsAsNeeded(it)
            )
        })

        Spacer(modifier = modifier.height(20.dp))


        AnimatedVisibility(visible = !state.selectedIfMedicationIsAsNeeded,
            enter = slideInHorizontally { -it } + fadeIn(),
            exit = slideOutHorizontally { -it } + fadeOut()) {
            Column {
                DoseTimeSettings(modifier,
                    state.listTimesForDay,
                    state.measurementUnit.abbreviation,
                    showCategorySettingsTime == ShowCategorySettingsTime.DAYS,
                    { showCategorySettingsTime = it }) { action, value ->

                    when (action) {
                        SetTimeIntervalForDayAction.SHOW_DIALOG -> {
                            showDialogSetTimeAndDosePlanning = true
                        }

                        SetTimeIntervalForDayAction.DELETE_ITEM -> {
                            if (value != null) addItemPlanningViewModel.onEvent(
                                DeleteToListTimesForDay(
                                    value
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = modifier.height(20.dp))
                DaySelection(modifier = modifier,
                    showCategorySettingsTime == ShowCategorySettingsTime.WEEKLY,
                    activateCard = { showCategorySettingsTime = it },
                    listDaysForWeek = state.listDayOfWeek,
                    otherAction = { action, value ->
                        when (action) {
                            DaySelectionAction.ADD_ITEM -> {
                                addItemPlanningViewModel.onEvent(
                                    AddToListDayOfWeek(value)
                                )
                            }

                            DaySelectionAction.DELETE_ITEM -> {
                                addItemPlanningViewModel.onEvent(
                                    DeleteToListDayOfWeek(value)
                                )
                            }

                            DaySelectionAction.SELECT_ALL -> {
                                addItemPlanningViewModel.onEvent(
                                    SelectAllToListDayOfWeek(value)
                                )
                            }

                            DaySelectionAction.UNSELECT_ALL -> {
                                addItemPlanningViewModel.onEvent(
                                    UnSelectAllToListDayOfWeek(value)
                                )
                            }
                        }
                    })

                Spacer(modifier = modifier.height(20.dp))

                PeriodOfUse(modifier = modifier,
                    startDateUse = state.startDateMedicineUse,
                    endDateUse = state.endDateMedicineUse,
                    showCategorySettingsTime == ShowCategorySettingsTime.PERIOD,
                    activateCard = { showCategorySettingsTime = it },
                    cardAction = { action, value ->
                        when (action) {
                            PeriodOfUseScreenAction.SAVE_START_DATE -> {
                                addItemPlanningViewModel.onEvent(UpdateStartDateUse(value))
                            }

                            PeriodOfUseScreenAction.SAVE_END_DATE -> {
                                addItemPlanningViewModel.onEvent(UpdateEndDateUse(value))
                            }
                        }
                    })
            }


        }
        Spacer(modifier = modifier.height(20.dp))
    }

    if (showDialogSetTimeAndDosePlanning) {

        SetTimeAndDosePlanning(state.measurementUnit.abbreviation) { action, value ->
            when (action) {
                SetTimeAndDosePlanningAction.CLOSE -> {
                    showDialogSetTimeAndDosePlanning = false
                }

                SetTimeAndDosePlanningAction.SAVE -> {
                    //TODO Опрацювати нуль в вигляді помилки
                    if (value != null) {
                        addItemPlanningViewModel.onEvent(AddToListTimesForDay(value))
                    }
                    showDialogSetTimeAndDosePlanning = false
                }
            }

        }
    }
}


@Composable
fun DemoMedicineCard(medicineName: String, reminderRestockMedicine: String?) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp), shape = ShapeDefaults.Large
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(20)
                    ), contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_pill_item),
                    contentDescription = "Icon",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(medicineName, style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(4.dp))
                if (reminderRestockMedicine != null) Text(
                    reminderRestockMedicine, style = MaterialTheme.typography.labelMedium
                )
            }
        }


    }
}



