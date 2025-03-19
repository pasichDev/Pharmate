package com.pasichdev.pharmate.presentation.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.pasichdev.pharmate.R
import com.pasichdev.pharmate.presentation.components.addItemToPlanning.CustomTopBar
import com.pasichdev.pharmate.presentation.components.addItemToPlanning.CustomTopBarAction
import com.pasichdev.pharmate.presentation.components.addItemToPlanning.DemoMedicineCard
import com.pasichdev.pharmate.presentation.components.addItemToPlanning.EditBasicInformation
import com.pasichdev.pharmate.presentation.components.addItemToPlanning.EditDosageInformation
import com.pasichdev.pharmate.presentation.components.addItemToPlanning.StageCreation
import com.pasichdev.pharmate.presentation.mvi.AddItemPlanningState
import com.pasichdev.pharmate.presentation.viewmodel.AddItemPlanningViewModel


@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun AddItemToPlanningScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier.padding(horizontal = 20.dp),
    addItemPlanningViewModel: AddItemPlanningViewModel = hiltViewModel()

) {
    var stageCreation by remember { mutableStateOf(StageCreation.BASIC_INFORMATION) }
    val state by addItemPlanningViewModel.state.collectAsStateWithLifecycle()

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

    fun isActiveNext(): Boolean {
        return when (stageCreation) {
            StageCreation.BASIC_INFORMATION -> state.nameMedicine.text.length in 3..25
            StageCreation.PERIOD -> isFinishEditDoseInformation(state)

        }
    }

    fun stageBack() {
        when (stageCreation) {
            StageCreation.BASIC_INFORMATION -> navController.popBackStack()
            StageCreation.PERIOD -> stageCreation = StageCreation.BASIC_INFORMATION
        }
    }


    fun stageNext() {
        when (stageCreation) {
            StageCreation.BASIC_INFORMATION -> {
                if (addItemPlanningViewModel.state.value.nameMedicine.text.length in 3..25) {
                    stageCreation = StageCreation.PERIOD
                }
            }

            StageCreation.PERIOD -> {

            }


        }
    }

    BackHandler {
        stageBack()
    }

    Column {
        CustomTopBar(stageCreation, isActiveNext()) {
            when (it) {
                CustomTopBarAction.NEXT -> stageNext()
                CustomTopBarAction.PREV -> stageBack()
            }
        }
        AnimatedVisibility(visible = stageCreation != StageCreation.BASIC_INFORMATION) {
            Column {
                Spacer(modifier = modifier.height(20.dp))
                DemoMedicineCard(
                    medicineName = state.nameMedicine.text,
                    reminderRestockMedicine = reminderRestockMedicine()
                )
                Spacer(modifier = modifier.height(20.dp))
            }

        }

        AnimatedContent(targetState = stageCreation, transitionSpec = {
            slideInHorizontally { it } + fadeIn() with slideOutHorizontally { -it } + fadeOut()
        }) { stage ->

            when (stage) {
                StageCreation.BASIC_INFORMATION -> {
                    EditBasicInformation(modifier = modifier)
                }

                StageCreation.PERIOD -> {
                    EditDosageInformation(
                        modifier = modifier

                    )
                }
            }

        }


    }


}

fun isFinishEditDoseInformation(addItemPlanningState: AddItemPlanningState): Boolean {

    if (addItemPlanningState.selectedIfMedicationIsAsNeeded) {
        return true
    } // Якщо вказано довільне вживання

    if (addItemPlanningState.listTimesForDay.isNotEmpty() && addItemPlanningState.listDayOfWeek.isNotEmpty() && addItemPlanningState.startDateMedicineUse.isNotEmpty() && addItemPlanningState.endDateMedicineUse.isNotEmpty()) {
        return true
    }
    return false
}



