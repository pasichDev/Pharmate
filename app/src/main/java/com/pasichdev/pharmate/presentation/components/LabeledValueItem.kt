package com.pasichdev.pharmate.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.pasichdev.pharmate.ui.theme.titlePlanningCard


@Composable
fun LabeledValueItem(
    itemTitle: String, value: String, enabled: Boolean = true, action: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .clickable { if (enabled) action() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            itemTitle,
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 8.dp),
            style = titlePlanningCard
        )

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(if (enabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline)
                .padding(vertical = 8.dp, horizontal = 20.dp)
        ) {
            Text(
                text = value,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.labelLarge
            )
        }

    }
}