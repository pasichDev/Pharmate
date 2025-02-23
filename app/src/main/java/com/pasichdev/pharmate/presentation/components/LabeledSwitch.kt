package com.pasichdev.pharmate.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pasichdev.pharmate.ui.theme.titlePlanningCard

enum class StylePlanningTitleSwitch { COLUMN, ROW }

@Composable
fun LabeledSwitch(
    itemTitle: String,
    value: Boolean,
    style: StylePlanningTitleSwitch = StylePlanningTitleSwitch.ROW,
    action: (Boolean) -> Unit
) {

    var weightSwitch = 0.15f
    var weightTitle = if (style == StylePlanningTitleSwitch.ROW) 0.75f else 1f
    var columnPadding =
        if (style == StylePlanningTitleSwitch.ROW) PaddingValues(horizontal = 20.dp) else PaddingValues(
            20.dp
        )

    @Composable
    fun switch(modifier: Modifier) {
        Switch(
            modifier = modifier, checked = value, onCheckedChange = {
                action(it)
            }, colors = SwitchDefaults.colors(
                uncheckedThumbColor = MaterialTheme.colorScheme.error,
                uncheckedBorderColor = MaterialTheme.colorScheme.error
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(columnPadding)

    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                itemTitle, Modifier.weight(weightTitle), style = titlePlanningCard
            )
            if (style == StylePlanningTitleSwitch.ROW) switch(Modifier.weight(weightSwitch))
        }

        if (style == StylePlanningTitleSwitch.COLUMN) {
            Spacer(Modifier.height(5.dp))
            switch(Modifier)
        }
    }
}