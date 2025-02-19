package com.pasichdev.pharmate.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun PreviewChip(textTrue: String, textFalse: String, isPreview: Boolean, onClick: () -> Unit) {
    var activeColor =
        if (isPreview) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary

    AssistChip(modifier = Modifier.padding(top = 5.dp), onClick = { onClick() }, label = {
        Text(
            if (isPreview) textTrue else textFalse, style = MaterialTheme.typography.labelMedium
        )
    }, colors = AssistChipDefaults.assistChipColors(
        labelColor = activeColor
    ), border = AssistChipDefaults.assistChipBorder(
        enabled = true, borderColor = activeColor
    )
    )


}