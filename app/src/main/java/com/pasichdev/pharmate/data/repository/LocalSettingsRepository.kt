package com.pasichdev.pharmate.data.repository


import com.pasichdev.pharmate.data.datastore.LocalDatastore
import com.pasichdev.pharmate.data.datastore.ThemeType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalSettingsRepository @Inject constructor(
    private val localDatastore: LocalDatastore
) {

    val combinedThemeSettingsFlow: Flow<Pair<Boolean, ThemeType>> = combine(
        localDatastore.isDynamicThemeFlow, localDatastore.themeTypeFlow
    ) { isDynamic, themeType ->
        isDynamic to themeType
    }

    suspend fun updateDynamicTheme(value: Boolean) {
        localDatastore.updateDynamicTheme(value)
    }

    suspend fun updateThemeType(value: ThemeType) {
        localDatastore.updateThemeType(value)
    }
}
