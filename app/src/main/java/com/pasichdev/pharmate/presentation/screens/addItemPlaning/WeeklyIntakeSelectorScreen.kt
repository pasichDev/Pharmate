package com.pasichdev.pharmate.presentation.screens.addItemPlaning

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
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

enum class WeeklyIntakeSelectorAction {
    ADD_ITEM, DELETE_ITEM, SELECT_ALL, UNSELECT_ALL
}

fun getDaysOfWeek(): List<DayOfWeek> {
    return DateFormatSymbols.getInstance().weekdays.filter { it.isNotEmpty() }
        .mapIndexed { index, name ->
            DayOfWeek(index, name.replaceFirstChar { it.uppercaseChar() })
        }

}


@Composable
fun WeeklyIntakeSelectorScreen(
    modifier: Modifier = Modifier,
    visibilityFull: Boolean = false,
    listDaysForWeek: List<Int>,
    activateCard: (ShowCategorySettingsTime) -> Unit,
    otherAction: (WeeklyIntakeSelectorAction, Int) -> Unit,
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
                WeeklyIntakeSelectorChipGroup(selectItems = listDaysForWeek,
                    allDaysActive = allDaysActive,
                    onClick = { item, selected ->
                        if (selected) otherAction(WeeklyIntakeSelectorAction.DELETE_ITEM, item)
                        else otherAction(WeeklyIntakeSelectorAction.ADD_ITEM, item)
                    },
                    onClickAll = {
                        if (allDaysActive) otherAction(WeeklyIntakeSelectorAction.UNSELECT_ALL, 0)
                        else otherAction(WeeklyIntakeSelectorAction.SELECT_ALL, 0)
                    })
            }
        }
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WeeklyIntakeSelectorChipGroup(
    selectItems: List<Int>,
    allDaysActive: Boolean,
    onClick: (Int, Boolean) -> Unit,
    onClickAll: () -> Unit
) {
    val scrollState = rememberScrollState()
    val daysOfWeek = getDaysOfWeek()


    Row(
        modifier = Modifier
            .padding(top = 5.dp)
            .fillMaxWidth()
            .horizontalScroll(scrollState),
    ) {
        FilterChip(selected = allDaysActive, enabled = selectItems.size <= 1, onClick = {
            onClickAll()

        }, label = { Text(stringResource(R.string.daily)) }, leadingIcon = if (allDaysActive) {
            {
                Icon(
                    Icons.Default.Check,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            }
        } else null)


        if (!allDaysActive) daysOfWeek.forEach { item ->

            var selected = selectItems.contains(item.num)
            Spacer(Modifier.width(10.dp))
            AssistChip(
                onClick = {
                    onClick(item.num, selected)
                },
                label = {
                    Text(text = "${item.name} ")
                },
                colors = AssistChipDefaults.assistChipColors(
                    labelColor = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                ),
                border = AssistChipDefaults.assistChipBorder(
                    enabled = true,
                    borderColor = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(
                        alpha = 0.6f
                    )
                ),
                enabled = true,
            )
        }
    }
}
