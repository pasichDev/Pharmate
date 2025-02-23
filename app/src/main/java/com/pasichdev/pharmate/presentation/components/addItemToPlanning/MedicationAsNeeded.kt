package com.pasichdev.pharmate.presentation.components.addItemToPlanning

import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.pasichdev.pharmate.R
import com.pasichdev.pharmate.presentation.components.LabeledSwitch
import com.pasichdev.pharmate.presentation.components.StylePlanningTitleSwitch

@Composable
fun MedicationAsNeeded(
    modifier: Modifier = Modifier, value: Boolean, onSelect: (Boolean) -> Unit
) {
    Card(modifier = modifier) {
        LabeledSwitch(
            stringResource(R.string.medicine_take_as_needed_question),
            value,
            StylePlanningTitleSwitch.COLUMN,
        ) {
            onSelect(it)

        }
    }
}