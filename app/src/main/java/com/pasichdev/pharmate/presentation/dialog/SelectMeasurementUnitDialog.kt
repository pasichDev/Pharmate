package com.pasichdev.pharmate.presentation.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pasichdev.pharmate.domain.MeasurementUnit
import com.pasichdev.pharmate.domain.measurementUnits

enum class ActionMeasurementDialog {
    CLOSE, SAVE
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectMeasurementUnitDialog(
    sheetState: SheetState,
    actionMeasurementDialog: (ActionMeasurementDialog, MeasurementUnit?) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = {
            actionMeasurementDialog(
                ActionMeasurementDialog.CLOSE, null
            )
        }, sheetState = sheetState
    ) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(3), modifier = Modifier.padding(16.dp)
        ) {
            items(measurementUnits.size) { it ->
                var item = measurementUnits[it]
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            actionMeasurementDialog(ActionMeasurementDialog.SAVE, item)
                        },
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.elevatedCardElevation()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "${item.fullName}/${item.abbreviation}")
                    }
                }
            }
        }
    }
}