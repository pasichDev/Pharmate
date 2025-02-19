package com.pasichdev.pharmate.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pasichdev.pharmate.data.datastore.ThemeType
import com.pasichdev.pharmate.data.repository.LocalSettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


@HiltViewModel
class MenuViewModel @Inject constructor(var settingsRepository: LocalSettingsRepository) :
    ViewModel() {

    val themeSettingsFlow: StateFlow<Pair<Boolean, ThemeType>> =
        settingsRepository.combinedThemeSettingsFlow.stateIn(scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = runBlocking { settingsRepository.combinedThemeSettingsFlow.first() })


    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.UpdateIsDynamicTheme -> {
                viewModelScope.launch(Dispatchers.IO) { settingsRepository.updateDynamicTheme(event.value) }
            }

            is SettingsEvent.UpdateThemeType -> {
                viewModelScope.launch(Dispatchers.IO) {
                    settingsRepository.updateThemeType(event.themeType)
                }
            }


        }
    }


}

sealed class SettingsEvent {
    data class UpdateIsDynamicTheme(val value: Boolean) : SettingsEvent()
    data class UpdateThemeType(val themeType: ThemeType) : SettingsEvent()
}
