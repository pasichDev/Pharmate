package com.pasichdev.pharmate.presentation.components.addItemToPlanning

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pasichdev.pharmate.R
import com.pasichdev.pharmate.defaultAllDaysCode
import com.pasichdev.pharmate.domain.model.DayOfWeek
import com.pasichdev.pharmate.presentation.components.PreviewChip
import java.text.DateFormatSymbols

enum class DaySelectionAction {
    ADD_ITEM, DELETE_ITEM, SELECT_ALL, UNSELECT_ALL
}

fun getDaysOfWeek(): List<DayOfWeek> {
    return DateFormatSymbols.getInstance().weekdays.filter { it.isNotEmpty() }
        .mapIndexed { index, name ->
            DayOfWeek(index, name.replaceFirstChar { it.uppercaseChar() })
        }

}


@Composable
fun DaySelection(
    modifier: Modifier = Modifier,
    visibilityFull: Boolean = false,
    listDaysForWeek: List<Int>,
    activateCard: (ShowCategorySettingsTime) -> Unit,
    otherAction: (DaySelectionAction, Int) -> Unit,
) {

    var allDaysActive = listDaysForWeek.contains(defaultAllDaysCode)


    Card(modifier = modifier, onClick = { activateCard(ShowCategorySettingsTime.WEEKLY) }) {

        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                stringResource(R.string.weeklyIntakeSelectorTitle),
                style = MaterialTheme.typography.titleMedium
            )
            if (!visibilityFull) {
                PreviewChip(
                    textTrue = stringResource(R.string.noSett),
                    textFalse = if (allDaysActive) stringResource(R.string.daily) else stringResource(
                        R.string.setupCountDaysOfWeek, listDaysForWeek.count()
                    ),
                    isPreview = listDaysForWeek.isEmpty()
                ) {
                    activateCard(ShowCategorySettingsTime.WEEKLY)
                }
            }
            if (visibilityFull) {
                DaysOfWeekList(selectItems = listDaysForWeek,
                    allDaysActive = allDaysActive,
                    onClick = { item, selected ->
                        if (selected) otherAction(DaySelectionAction.DELETE_ITEM, item)
                        else otherAction(DaySelectionAction.ADD_ITEM, item)
                    },
                    onClickAll = {
                        if (allDaysActive) otherAction(DaySelectionAction.UNSELECT_ALL, 0)
                        else otherAction(DaySelectionAction.SELECT_ALL, 0)
                    })
            }
        }
    }
}

