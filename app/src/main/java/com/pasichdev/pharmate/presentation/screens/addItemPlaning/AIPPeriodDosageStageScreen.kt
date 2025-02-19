package com.pasichdev.pharmate.presentation.screens.addItemPlaning

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pasichdev.pharmate.R
import com.pasichdev.pharmate.presentation.dialog.SetTimeAndDosePlaning
import com.pasichdev.pharmate.presentation.dialog.SetTimeAndDosePlaningAction
import com.pasichdev.pharmate.presentation.viewmodel.AddItemPlaningViewModel
import com.pasichdev.pharmate.presentation.viewmodel.ItemPlaningEvent.*


enum class ShowCategorySettingsTime {
    IF_NECESSARY, DAYS, WEEKLY, PERIOD
}


@Composable
fun AIPPeriodDosageStageScreen(
    modifier: Modifier = Modifier,
    addItemPlaningViewModel: AddItemPlaningViewModel = hiltViewModel()

) {
    val state by addItemPlaningViewModel.state.collectAsStateWithLifecycle()
    var showCategorySettingsTime by remember { mutableStateOf(ShowCategorySettingsTime.IF_NECESSARY) }
    var showDialogSetTimeAndDosePlaning by remember { mutableStateOf(false) }

    @Composable
    fun reminderRestockMedicine(): String {
        return if (state.reminderRestockMedicine) {
            "${stringResource(R.string.current_stocks)}: ${state.currentStocks} ${state.measurementUnit.abbreviation}\n${
                stringResource(
                    R.string.remind_me_of_stock
                )
            }: ${state.remindMeOfStock} ${state.measurementUnit.abbreviation}"
        } else {
            stringResource(R.string.reminderRestockMedicineOff)
        }
    }

    Column {
        Spacer(modifier = modifier.height(20.dp))
        DemoMedicineCard(
            medicineName = state.nameMedicine.text,
            reminderRestockMedicine = reminderRestockMedicine()
        )
        Spacer(modifier = modifier.height(20.dp))

        IfMedicationIsAsNeededScreen(modifier = modifier,
            state.selectedIfMedicationIsAsNeeded,
            onSelect = {
                addItemPlaningViewModel.onEvent(
                    UpdateSelectedIfMedicationIsAsNeeded(it)
                )
            })

        Spacer(modifier = modifier.height(20.dp))


        AnimatedVisibility(visible = !state.selectedIfMedicationIsAsNeeded,
            enter = slideInHorizontally { -it } + fadeIn(),
            exit = slideOutHorizontally { -it } + fadeOut()) {
            Column {
                SetTimeIntervalForDayScreen(modifier,
                    state.listTimesForDay,
                    state.measurementUnit.abbreviation,
                    showCategorySettingsTime == ShowCategorySettingsTime.DAYS,
                    { showCategorySettingsTime = it }) { action, value ->

                    when (action) {
                        SetTimeIntervalForDayAction.SHOW_DIALOG -> {
                            showDialogSetTimeAndDosePlaning = true
                        }

                        SetTimeIntervalForDayAction.DELETE_ITEM -> {
                            if (value != null) addItemPlaningViewModel.onEvent(
                                DeleteToListTimesForDay(
                                    value
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = modifier.height(20.dp))
                WeeklyIntakeSelectorScreen(modifier = modifier,
                    showCategorySettingsTime == ShowCategorySettingsTime.WEEKLY,
                    activateCard = { showCategorySettingsTime = it },
                    listDaysForWeek = state.listDayOfWeek,
                    otherAction = { action, value ->
                        when (action) {
                            WeeklyIntakeSelectorAction.ADD_ITEM -> {
                                addItemPlaningViewModel.onEvent(
                                    AddToListDayOfWeek(value)
                                )
                            }

                            WeeklyIntakeSelectorAction.DELETE_ITEM -> {
                                addItemPlaningViewModel.onEvent(
                                    DeleteToListDayOfWeek(value)
                                )
                            }

                            WeeklyIntakeSelectorAction.SELECT_ALL -> {
                                addItemPlaningViewModel.onEvent(
                                    SelectAllToListDayOfWeek(value)
                                )
                            }

                            WeeklyIntakeSelectorAction.UNSELECT_ALL -> {
                                addItemPlaningViewModel.onEvent(
                                    UnSelectAllToListDayOfWeek(value)
                                )
                            }
                        }
                    })

                Spacer(modifier = modifier.height(20.dp))

                PeriodOfUseScreen(modifier = modifier,
                    startDateUse = state.startDateMedicineUse,
                    endDateUse = state.endDateMedicineUse,
                    showCategorySettingsTime == ShowCategorySettingsTime.PERIOD,
                    activateCard = { showCategorySettingsTime = it },
                    cardAction = { action, value ->
                        when (action) {
                            PeriodOfUseScreenAction.SAVE_START_DATE -> {
                                addItemPlaningViewModel.onEvent(UpdateStartDateUse(value))
                            }

                            PeriodOfUseScreenAction.SAVE_END_DATE -> {
                                addItemPlaningViewModel.onEvent(UpdateEndDateUse(value))
                            }
                        }
                    })
            }


        }
        Spacer(modifier = modifier.height(20.dp))
    }

    if (showDialogSetTimeAndDosePlaning) {

        SetTimeAndDosePlaning(state.measurementUnit.abbreviation) { action, value ->
            when (action) {
                SetTimeAndDosePlaningAction.CLOSE -> {
                    showDialogSetTimeAndDosePlaning = false
                }

                SetTimeAndDosePlaningAction.SAVE -> {
                    //TODO Опрацювати нуль в вигляді помилки
                    if (value != null) {
                        addItemPlaningViewModel.onEvent(AddToListTimesForDay(value))
                    }
                    showDialogSetTimeAndDosePlaning = false
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



