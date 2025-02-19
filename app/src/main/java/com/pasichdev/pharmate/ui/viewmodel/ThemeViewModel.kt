package com.pasichdev.pharmate.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pasichdev.pharmate.data.datastore.ThemeType
import com.pasichdev.pharmate.data.repository.LocalSettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


@HiltViewModel
class ThemeViewModel @Inject constructor(settingsRepository: LocalSettingsRepository) :
    ViewModel() {

    val themeSettingsFlow: StateFlow<Pair<Boolean, ThemeType>> =
        settingsRepository.combinedThemeSettingsFlow.stateIn(scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = runBlocking { settingsRepository.combinedThemeSettingsFlow.first() })

}