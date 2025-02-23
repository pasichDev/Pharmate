package com.pasichdev.pharmate.presentation.components.addItemToPlanning

import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pasichdev.pharmate.R


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DaysOfWeekList(
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
