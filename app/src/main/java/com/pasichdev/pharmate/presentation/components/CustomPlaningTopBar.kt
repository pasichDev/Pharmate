package com.pasichdev.pharmate.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pasichdev.pharmate.R
import com.pasichdev.pharmate.presentation.screens.addItemPlaning.StageCreation

enum class CustomTopBarAction {
    NEXT, PREV
}

@Composable
fun CustomTopBar(stageCreation: StageCreation, action: (CustomTopBarAction) -> Unit) {

    fun nameActionNext(): Int {
        return when (stageCreation) {
            StageCreation.NAME_UNITS -> R.string.next_step
            StageCreation.PERIOD_DOSAGE -> R.string.add_medicine
        }
    }

    fun subTitle(): Int {
        return when (stageCreation) {
            StageCreation.NAME_UNITS -> R.string.planing_subtitle_first
            StageCreation.PERIOD_DOSAGE -> R.string.planing_subtitle_second
        }
    }

    fun title(): String {
        return when (stageCreation) {
            StageCreation.NAME_UNITS -> "1/2"
            StageCreation.PERIOD_DOSAGE -> "2/2"
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center
    ) {
        Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { action(CustomTopBarAction.PREV) }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")

            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                Modifier.weight(1f),
            ) {
                Text(
                    title(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    stringResource(subTitle()),
                    style = MaterialTheme.typography.labelMedium,
                )
            }

            //TODO Якщо поле вводу на перші сторінці не заповнене тоді декативувати кнопку next
            when (stageCreation) {
                StageCreation.NAME_UNITS -> IconButton(onClick = { action(CustomTopBarAction.NEXT) }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "next")

                }

                StageCreation.PERIOD_DOSAGE -> SmallNavButton(
                    onClick = {
                        action(CustomTopBarAction.NEXT)
                    }, name = stringResource(nameActionNext())
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

        }

    }
}