package com.pasichdev.pharmate.presentation.components.addItemToPlanning

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pasichdev.pharmate.R
import com.pasichdev.pharmate.domain.model.MedicationDoseInfo
import kotlinx.coroutines.delay
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DoseIntervalList (
    items: List<MedicationDoseInfo>,
    measurementUnit: String,
    onAddClick: () -> Unit,
    onTrailingClick: (MedicationDoseInfo) -> Unit
) {
    var selectedChip by remember { mutableStateOf<MedicationDoseInfo?>(null) }
    val sortedItems =
        items.sortedBy { LocalTime.parse(it.time, DateTimeFormatter.ofPattern("HH:mm")) }
    val scrollState = rememberScrollState()


    LaunchedEffect(selectedChip) {
        selectedChip?.let {
            delay(2000)
            selectedChip = null
        }
    }

    Row(
        modifier = Modifier
            .padding(top = 5.dp)
            .fillMaxWidth()
            .horizontalScroll(scrollState),
    ) {
        AssistChip(onClick = onAddClick,
            label = { Text(text = stringResource(R.string.add)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add)
                )
            })


        sortedItems.forEach { item ->

            Spacer(Modifier.width(10.dp))
            AssistChip(
                onClick = { selectedChip = if (selectedChip == item) null else item },
                label = {
                    if (selectedChip == item) {
                        Icon(Icons.Default.Clear,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                onTrailingClick(item)
                            })
                    } else {
                        Text(text = "${item.time} / ${item.dose} $measurementUnit")
                    }
                },
                colors = AssistChipDefaults.assistChipColors(
                    labelColor = if (selectedChip == item) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onBackground
                ),
                border = AssistChipDefaults.assistChipBorder(
                    enabled = true,
                    borderColor = if (selectedChip == item) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface.copy(
                        alpha = 0.6f
                    )
                ),
                enabled = true,
            )
        }
    }
}