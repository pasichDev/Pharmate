package com.pasichdev.pharmate.presentation.components.addItemToPlanning

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
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

enum class CustomTopBarAction {
    NEXT, PREV
}

@Composable
fun CustomTopBar(
    stageCreation: StageCreation, isActiveNext: Boolean, action: (CustomTopBarAction) -> Unit
) {


    fun subTitle(): Int {
        return when (stageCreation) {
            StageCreation.BASIC_INFORMATION -> R.string.Planning_subtitle_first
            StageCreation.PERIOD -> R.string.Planning_subtitle_second
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
                    "${(stageCreation.ordinal + 1)}/${StageCreation.entries.size}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    stringResource(subTitle()),
                    style = MaterialTheme.typography.labelMedium,
                )
            }

               IconButton(onClick = { action(CustomTopBarAction.NEXT) }, enabled = isActiveNext) {
                    Icon(
                       if(stageCreation == StageCreation.PERIOD ) Icons.Default.Check else  Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = stringResource(R.string.next_step)
                    )

                }

            Spacer(modifier = Modifier.width(10.dp))

        }

    }
}