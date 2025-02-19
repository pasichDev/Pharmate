package com.pasichdev.pharmate.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

enum class ThemeType { SYSTEM, LIGHT, DARK }

class LocalDatastore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        private val IS_DYNAMIC_THEME = booleanPreferencesKey("dynamic_theme")
        private val THEME_TYPE = intPreferencesKey("theme_type")
    }

    val isDynamicThemeFlow: Flow<Boolean> =
        dataStore.data.map { preferences -> preferences[IS_DYNAMIC_THEME] == true }

    val themeTypeFlow: Flow<ThemeType> = dataStore.data.map { preferences ->
        ThemeType.entries.getOrElse(
            preferences[THEME_TYPE] ?: 0
        ) { ThemeType.SYSTEM }
    }

    val combinedThemeSettingsFlow: Flow<Pair<Boolean, ThemeType>> =
        combine(isDynamicThemeFlow, themeTypeFlow) { isDynamic, themeType ->
            isDynamic to themeType
        }

    suspend fun updateDynamicTheme(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_DYNAMIC_THEME] = value
        }
    }

    suspend fun updateThemeType(value: ThemeType) {
        dataStore.edit { preferences ->
            preferences[THEME_TYPE] = value.ordinal
        }
    }
}
