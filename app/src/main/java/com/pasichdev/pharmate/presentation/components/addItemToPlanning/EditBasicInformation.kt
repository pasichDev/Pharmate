package com.pasichdev.pharmate.presentation.components.addItemToPlanning

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pasichdev.pharmate.R
import com.pasichdev.pharmate.presentation.components.LabeledSwitch
import com.pasichdev.pharmate.presentation.components.LabeledValueItem
import com.pasichdev.pharmate.presentation.components.TipWidget
import com.pasichdev.pharmate.presentation.dialog.ActionMeasurementDialog
import com.pasichdev.pharmate.presentation.dialog.RemainingQuantityDialog
import com.pasichdev.pharmate.presentation.dialog.RemainingQuantityDialogAction
import com.pasichdev.pharmate.presentation.dialog.RemainingQuantityDialogState
import com.pasichdev.pharmate.presentation.dialog.SelectMeasurementUnitDialog
import com.pasichdev.pharmate.presentation.mvi.AddItemPlanningState
import com.pasichdev.pharmate.presentation.viewmodel.AddItemPlanningViewModel
import com.pasichdev.pharmate.presentation.viewmodel.ItemPlanningEvent
import com.pasichdev.pharmate.presentation.viewmodel.ItemPlanningEvent.UpdateMeasurementUnit
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditBasicInformation(
    modifier: Modifier = Modifier,
    addItemPlanningViewModel: AddItemPlanningViewModel = hiltViewModel()
) {

    val state by addItemPlanningViewModel.state.collectAsStateWithLifecycle()
    var isError by remember { mutableStateOf(false) }
    var remainingQuantityDialogState by remember { mutableStateOf(RemainingQuantityDialogState.NONE) }
    val coroutineScope = rememberCoroutineScope()
    var showSelectMeasurementUnitDialog by remember { mutableStateOf(false) }
    val sheetStateSelectMeasurementUnitDialog = rememberModalBottomSheetState()



    Column {
        TipWidget(tip = stringResource(R.string.enter_name_unit))
        Spacer(modifier = modifier.height(15.dp))
        Card(
            modifier = modifier.fillMaxWidth(), shape = ShapeDefaults.Large
        ) {
            Spacer(modifier = modifier.height(10.dp))
            OutlinedTextField(modifier = modifier.fillMaxWidth(),
                maxLines = 1,
                value = state.nameMedicine,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                onValueChange = {
                    if (it.text.length <= 25) {
                        addItemPlanningViewModel.onEvent(ItemPlanningEvent.UpdateNameMedicine(it))
                    }
                    isError = it.text.length !in 3..25
                },
                label = { Text(stringResource(R.string.name_medicine)) },
                isError = isError,
                supportingText = {
                    if (isError) {
                        Text(
                            text = stringResource(R.string.error_name_length),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                })
            Spacer(modifier = Modifier.height(10.dp))
            LabeledValueItem (
                itemTitle = stringResource(R.string.units),
                value = state.measurementUnit.abbreviation
            ) { coroutineScope.launch {
                showSelectMeasurementUnitDialog = true

            }}

            Spacer(modifier = Modifier.height(20.dp))
        }

        Spacer(modifier = Modifier.height(20.dp))
        Card(
            modifier = modifier.fillMaxWidth(), shape = ShapeDefaults.Large
        ) {

            Spacer(modifier = modifier.height(10.dp))

            LabeledSwitch (
                stringResource(R.string.reminder_restock_question),
                state.reminderRestockMedicine
            ) {
                addItemPlanningViewModel.onEvent(
                    ItemPlanningEvent.UpdateReminderRestockMedicine(
                        it
                    )
                )
            }

            Spacer(modifier = modifier.height(10.dp))

            if (state.reminderRestockMedicine) {
                Column {
                    Spacer(modifier = Modifier.height(10.dp))
                    LabeledValueItem (
                        itemTitle = stringResource(R.string.current_stocks),
                        value = "${state.currentStocks} ${state.measurementUnit.abbreviation}"
                    ) {
                        coroutineScope.launch {
                            remainingQuantityDialogState =
                                RemainingQuantityDialogState.SHOW_DIALOG_CURRENT_STOCK_VALUE

                        }


                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    LabeledValueItem (
                        itemTitle = stringResource(R.string.remind_me_of_stock),
                        value = "${state.remindMeOfStock} ${state.measurementUnit.abbreviation}"
                    ) {
                        remainingQuantityDialogState =
                            RemainingQuantityDialogState.SHOW_DIALOG_REMIND_ME_STOCK_VALUE
                    }

                    Spacer(modifier = Modifier.height(20.dp))


                }
            }

        }
    }





    if (remainingQuantityDialogState != RemainingQuantityDialogState.NONE) {
        RemainingQuantityDialog(titleDialog = stringResource(
            remainingQuantityDefaultTitle(
                remainingQuantityDialogState
            )
        ),
            defValue = remainingQuantityDefaultValue(remainingQuantityDialogState, state),
            measurementUnit = state.measurementUnit.abbreviation,
            remainingQuantityDialogState = remainingQuantityDialogState,
            action = { actions, state, value ->
                when (actions) {
                    RemainingQuantityDialogAction.CANCEL -> {
                        coroutineScope.launch {
                            remainingQuantityDialogState = RemainingQuantityDialogState.NONE
                        }
                    }

                    RemainingQuantityDialogAction.SAVE -> {
                        when (state) {
                            RemainingQuantityDialogState.SHOW_DIALOG_CURRENT_STOCK_VALUE -> {
                                addItemPlanningViewModel.onEvent(
                                    ItemPlanningEvent.UpdateCurrentStocks(value)
                                )
                            }

                            RemainingQuantityDialogState.SHOW_DIALOG_REMIND_ME_STOCK_VALUE -> {
                                addItemPlanningViewModel.onEvent(
                                    ItemPlanningEvent.UpdateRemindMeOfStock(value)
                                )
                            }

                            RemainingQuantityDialogState.NONE -> Unit
                        }
                        remainingQuantityDialogState = RemainingQuantityDialogState.NONE
                    }
                }
            })

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

                    if (value != null) addItemPlanningViewModel.onEvent(
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

fun remainingQuantityDefaultValue(
    remainingQuantityDialogState: RemainingQuantityDialogState, state: AddItemPlanningState
): Int {
    return when (remainingQuantityDialogState) {
        RemainingQuantityDialogState.SHOW_DIALOG_CURRENT_STOCK_VALUE -> state.currentStocks
        RemainingQuantityDialogState.SHOW_DIALOG_REMIND_ME_STOCK_VALUE -> state.remindMeOfStock
        RemainingQuantityDialogState.NONE -> 0
    }
}

fun remainingQuantityDefaultTitle(remainingQuantityDialogState: RemainingQuantityDialogState): Int {
    return when (remainingQuantityDialogState) {
        RemainingQuantityDialogState.SHOW_DIALOG_CURRENT_STOCK_VALUE -> R.string.current_stock_title
        RemainingQuantityDialogState.SHOW_DIALOG_REMIND_ME_STOCK_VALUE -> R.string.low_stock_threshold_title
        RemainingQuantityDialogState.NONE -> R.string.error
    }
}