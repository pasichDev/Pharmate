package com.pasichdev.pharmate.presentation.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pasichdev.pharmate.R
import com.pasichdev.pharmate.data.datastore.ThemeType
import com.pasichdev.pharmate.domain.model.getThemeTypeList

enum class ThemeColorDialogAction {
    CLOSE, UPDATE
}


@Composable
fun ThemeColorDialog(action: (ThemeColorDialogAction, ThemeType) -> Unit, themeType: ThemeType) {
    AlertDialog(onDismissRequest = { action(ThemeColorDialogAction.CLOSE, ThemeType.SYSTEM) },
        title = { Text(text = stringResource(R.string.theme_dialog_title)) },
        text = {
            RadioButtons(
                select = {
                    action(ThemeColorDialogAction.UPDATE, it)
                },
                themeType,
            )
        },
        confirmButton = {
            TextButton(onClick = { action(ThemeColorDialogAction.CLOSE, ThemeType.SYSTEM) }) {
                Text(
                    stringResource(R.string.cancel)
                )
            }
        })
}

@Composable
fun RadioButtons(select: (ThemeType) -> Unit, themeType: ThemeType) {


    Column {
        getThemeTypeList().forEach { item ->
            Row(modifier = Modifier
                .fillMaxWidth()
                .selectable(selected = (item.value == themeType), onClick = {
                    select(item.value)
                }), verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = (item.value == themeType), onClick = {
                    select(item.value)
                })
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}



