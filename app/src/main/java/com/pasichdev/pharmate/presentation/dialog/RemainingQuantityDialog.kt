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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.pasichdev.pharmate.presentation.components.QuantityInputField

enum class RemainingQuantityDialogAction {
    CANCEL, SAVE
}

enum class RemainingQuantityDialogState {
    SHOW_DIALOG_CURRENT_STOCK_VALUE, SHOW_DIALOG_REMIND_ME_STOCK_VALUE, NONE
}

@Composable
fun RemainingQuantityDialog(
    titleDialog: String,
    defValue: Int,
    measurementUnit: String,
    remainingQuantityDialogState: RemainingQuantityDialogState,
    action: (RemainingQuantityDialogAction, RemainingQuantityDialogState, Int) -> Unit
) {
    var value by remember { mutableStateOf(defValue.toString()) }

    Dialog(
        onDismissRequest = {
            action(
                RemainingQuantityDialogAction.CANCEL, remainingQuantityDialogState, 0
            )
        }, properties = DialogProperties(usePlatformDefaultWidth = true)
    ) {
        ElevatedCard(
            modifier = Modifier.background(
                color = MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.extraLarge
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            shape = MaterialTheme.shapes.extraLarge
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp), text = titleDialog
                )
                QuantityInputField(
                    value = value, onValueChange = { value = it }, unit = measurementUnit
                )


                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End
                ) {
                    Button(modifier = Modifier.padding(end = 8.dp), onClick = {
                        action(
                            RemainingQuantityDialogAction.CANCEL, remainingQuantityDialogState, 0
                        )
                    }) {
                        Text(
                            text = stringResource(R.string.cancel)
                        )
                    }
                    Button(modifier = Modifier.padding(start = 8.dp), onClick = {
                        action(
                            RemainingQuantityDialogAction.SAVE,
                            remainingQuantityDialogState,
                            value.toIntOrNull() ?: 0
                        )
                    }) {
                        Text(text = stringResource(R.string.save))
                    }
                }
            }
        }
    }
}
