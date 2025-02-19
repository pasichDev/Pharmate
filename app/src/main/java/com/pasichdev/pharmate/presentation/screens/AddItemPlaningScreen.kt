package com.pasichdev.pharmate.presentation.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.pasichdev.pharmate.presentation.components.CustomTopBar
import com.pasichdev.pharmate.presentation.components.CustomTopBarAction
import com.pasichdev.pharmate.presentation.dialog.ActionMeasurementDialog
import com.pasichdev.pharmate.presentation.dialog.SelectMeasurementUnitDialog
import com.pasichdev.pharmate.presentation.screens.addItemPlaning.AIPFirstStageActions
import com.pasichdev.pharmate.presentation.screens.addItemPlaning.AIPFirstStageScreen
import com.pasichdev.pharmate.presentation.screens.addItemPlaning.AIPPeriodDosageStageScreen
import com.pasichdev.pharmate.presentation.screens.addItemPlaning.StageCreation
import com.pasichdev.pharmate.presentation.viewmodel.AddItemPlaningViewModel
import com.pasichdev.pharmate.presentation.viewmodel.ItemPlaningEvent.UpdateMeasurementUnit
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun AddItemPlaningScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier.padding(horizontal = 20.dp),
    addItemPlaningViewModel: AddItemPlaningViewModel = hiltViewModel()

) {
    var stageCreation by remember { mutableStateOf(StageCreation.NAME_UNITS) }
    val scope = rememberCoroutineScope()

    var showSelectMeasurementUnitDialog by remember { mutableStateOf(false) }
    val sheetStateSelectMeasurementUnitDialog = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()


    fun stageBack() {
        when (stageCreation) {
            StageCreation.NAME_UNITS -> {
                navController.popBackStack()
            }

            StageCreation.PERIOD_DOSAGE -> stageCreation = StageCreation.NAME_UNITS
        }
    }


    fun stageNext() {
        when (stageCreation) {
            StageCreation.NAME_UNITS -> {
                if (addItemPlaningViewModel.state.value.nameMedicine.text.length in 3..25) {
                    stageCreation = StageCreation.PERIOD_DOSAGE
                }
            }

            StageCreation.PERIOD_DOSAGE -> {}
        }
    }

    BackHandler {
        stageBack()
    }

    Column {
        CustomTopBar(stageCreation) {
            when (it) {
                CustomTopBarAction.NEXT -> stageNext()
                CustomTopBarAction.PREV -> stageBack()
            }
        }

        AnimatedContent(targetState = stageCreation, transitionSpec = {
            slideInHorizontally { it } + fadeIn() with slideOutHorizontally { -it } + fadeOut()


        }) { stage ->

            when (stage) {
                StageCreation.NAME_UNITS -> {
                    AIPFirstStageScreen(modifier = modifier, actions = { action, value ->
                        when (action) {
                            AIPFirstStageActions.SHOW_DIALOG_DOSAGE_UNIT -> {
                                scope.launch {
                                    showSelectMeasurementUnitDialog = true

                                }
                            }

                        }
                    })
                }

                StageCreation.PERIOD_DOSAGE -> {
                    AIPPeriodDosageStageScreen(
                        modifier = modifier

                    )
                }
            }

        }


    }


    if (showSelectMeasurementUnitDialog) {
        SelectMeasurementUnitDialog(sheetStateSelectMeasurementUnitDialog) { action, value ->

            when (action) {
                ActionMeasurementDialog.CLOSE -> {
                    coroutineScope.launch { sheetStateSelectMeasurementUnitDialog.hide() }
                        .invokeOnCompletion {
                            if (!sheetStateSelectMeasurementUnitDialog.isVisible) {
                                showSelectMeasurementUnitDialog = false
                            }
                        }
                }

                ActionMeasurementDialog.SAVE -> {

                    coroutineScope.launch { sheetStateSelectMeasurementUnitDialog.hide() }
                        .invokeOnCompletion {
                            if (!sheetStateSelectMeasurementUnitDialog.isVisible) {
                                showSelectMeasurementUnitDialog = false
                            }
                        }

                    if (value != null) addItemPlaningViewModel.onEvent(
                        UpdateMeasurementUnit(
                            value
                        )
                    )
                    ///TODO додати якщо нуь повідоблен в ботомо шит що щось трапилось
                }
            }

        }
    }


}

