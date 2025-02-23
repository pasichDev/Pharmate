package com.pasichdev.pharmate.presentation.components.addItemToPlanning

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pasichdev.pharmate.R
import com.pasichdev.pharmate.domain.model.MedicationDoseInfo
import com.pasichdev.pharmate.presentation.components.PreviewChip
import com.pasichdev.pharmate.ui.theme.titlePlanningCard


enum class SetTimeIntervalForDayAction {
    SHOW_DIALOG, DELETE_ITEM
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoseTimeSettings(
    modifier: Modifier = Modifier,
    listTimesForDay: List<MedicationDoseInfo>,
    measurementUnit: String,
    visibilityFull: Boolean = false,
    activateCard: (ShowCategorySettingsTime) -> Unit,
    otherAction: (SetTimeIntervalForDayAction, MedicationDoseInfo?) -> Unit
) {


    Card(modifier = modifier, onClick = { activateCard(ShowCategorySettingsTime.DAYS) }) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                stringResource(R.string.daily_dosage_frequency), style = titlePlanningCard
            )

            if (!visibilityFull) {
                PreviewChip(
                    textTrue = stringResource(R.string.noSett), textFalse = stringResource(
                        R.string.setupCount, listTimesForDay.count()
                    ), isPreview = listTimesForDay.isEmpty()
                ) {
                    if (listTimesForDay.isEmpty()) {
                        activateCard(ShowCategorySettingsTime.DAYS)
                        otherAction(SetTimeIntervalForDayAction.SHOW_DIALOG, null)
                    } else {
                        activateCard(ShowCategorySettingsTime.DAYS)
                    }
                }

            }

            if (visibilityFull) {
                DoseIntervalList(items = listTimesForDay,
                    measurementUnit = measurementUnit,
                    onAddClick = { otherAction(SetTimeIntervalForDayAction.SHOW_DIALOG, null) },
                    onTrailingClick = { otherAction(SetTimeIntervalForDayAction.DELETE_ITEM, it) })
            }

        }
    }


}