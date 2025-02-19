package com.pasichdev.pharmate.presentation.screens.addItemPlaning

import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.pasichdev.pharmate.R
import com.pasichdev.pharmate.presentation.components.planing.PlaningTitleSwitch
import com.pasichdev.pharmate.presentation.components.planing.StylePlaningTitleSwitch

@Composable
fun IfMedicationIsAsNeededScreen(
    modifier: Modifier = Modifier, value: Boolean, onSelect: (Boolean) -> Unit
) {
    Card(modifier = modifier) {
        PlaningTitleSwitch(
            stringResource(R.string.medicine_take_as_needed_question),
            value,
            StylePlaningTitleSwitch.COLUMN,
        ) {
            onSelect(it)

        }


    }
}