package com.pasichdev.pharmate.presentation.components.planing

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
import com.pasichdev.pharmate.ui.theme.titlePlaningCard

enum class StylePlaningTitleSwitch { COLUMN, ROW }

@Composable
fun PlaningTitleSwitch(
    itemTitle: String,
    value: Boolean,
    style: StylePlaningTitleSwitch = StylePlaningTitleSwitch.ROW,
    action: (Boolean) -> Unit
) {

    var weightSwitch = 0.15f
    var weightTitle = if (style == StylePlaningTitleSwitch.ROW) 0.75f else 1f
    var columnPadding =
        if (style == StylePlaningTitleSwitch.ROW) PaddingValues(horizontal = 20.dp) else PaddingValues(
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
                itemTitle, Modifier.weight(weightTitle), style = titlePlaningCard
            )
            if (style == StylePlaningTitleSwitch.ROW) switch(Modifier.weight(weightSwitch))
        }

        if (style == StylePlaningTitleSwitch.COLUMN) {
            Spacer(Modifier.height(5.dp))
            switch(Modifier)
        }
    }
}