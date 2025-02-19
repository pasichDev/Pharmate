package com.pasichdev.pharmate.presentation.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SmallNavButton(onClick: () -> Unit, name: String){
    TextButton(
        onClick = onClick, colors = ButtonDefaults.textButtonColors(
            containerColor = MaterialTheme.colorScheme.primary

        ), shape = RoundedCornerShape(10.dp)
    ) {
        Text(
           name, style = MaterialTheme.typography.labelLarge.copy(
                color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold
            )
        )
    }
}