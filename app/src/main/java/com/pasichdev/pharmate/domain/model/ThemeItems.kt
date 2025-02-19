package com.pasichdev.pharmate.domain.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.pasichdev.pharmate.data.datastore.ThemeType
import com.pasichdev.pharmate.R


data class RadioItem(val value: ThemeType, val title: String)

@Composable
fun getThemeTypeLabel(themeType: ThemeType): String {
    return when (themeType) {
        ThemeType.SYSTEM -> stringResource(R.string.theme_type_system)
        ThemeType.LIGHT -> stringResource(R.string.theme_type_light)
        ThemeType.DARK -> stringResource(R.string.theme_type_dark)
    }
}

@Composable
fun getThemeTypeList(): List<RadioItem> {
    return listOf(
        RadioItem(ThemeType.SYSTEM, stringResource(R.string.theme_type_system)),
        RadioItem(ThemeType.LIGHT, stringResource(R.string.theme_type_light)),
        RadioItem(ThemeType.DARK, stringResource(R.string.theme_type_dark)),
    )

}