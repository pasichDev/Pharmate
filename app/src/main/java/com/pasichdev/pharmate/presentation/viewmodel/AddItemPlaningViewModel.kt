package com.pasichdev.pharmate.presentation.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.pasichdev.pharmate.defaultAllDaysCode
import com.pasichdev.pharmate.domain.MeasurementUnit
import com.pasichdev.pharmate.domain.model.MedicationDoseInfo
import com.pasichdev.pharmate.presentation.mvi.AddItemPlaningState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class AddItemPlaningViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(AddItemPlaningState())
    val state: StateFlow<AddItemPlaningState> = _state.asStateFlow()

    fun onEvent(event: ItemPlaningEvent) {
        when (event) {
            is ItemPlaningEvent.UpdateMeasurementUnit -> {
                _state.update { it.copy(measurementUnit = event.measurementUnit) }
            }

            is ItemPlaningEvent.UpdateNameMedicine -> {
                _state.update { it.copy(nameMedicine = event.nameMedicine) }
            }

            is ItemPlaningEvent.UpdateSelectedIfMedicationIsAsNeeded -> {
                _state.update { it.copy(selectedIfMedicationIsAsNeeded = event.value) }
            }

            is ItemPlaningEvent.UpdateReminderRestockMedicine -> {
                _state.update { it.copy(reminderRestockMedicine = event.value) }
            }

            is ItemPlaningEvent.UpdateCurrentStocks -> {
                _state.update { it.copy(currentStocks = event.value) }
            }

            is ItemPlaningEvent.UpdateRemindMeOfStock -> {
                _state.update { it.copy(remindMeOfStock = event.value) }
            }

            is ItemPlaningEvent.AddToListTimesForDay -> {
                _state.update {

                    it.copy(listTimesForDay = it.listTimesForDay.toMutableList().apply {
                        if (!contains(event.value)) {
                            add(event.value)
                        }
                    })
                }

            }

            is ItemPlaningEvent.DeleteToListTimesForDay -> {
                _state.update {
                    it.copy(listTimesForDay = it.listTimesForDay.toMutableList()
                        .apply { remove(event.value) })
                }
            }

            is ItemPlaningEvent.AddToListDayOfWeek -> {
                _state.update {

                    it.copy(listDayOfWeek = it.listDayOfWeek.toMutableList().apply {
                        if (!contains(event.value)) {
                            add(event.value)
                        }
                    })
                }
            }

            is ItemPlaningEvent.DeleteToListDayOfWeek -> {
                    _state.update {
                        it.copy(listDayOfWeek = it.listDayOfWeek.toMutableList()
                            .apply { remove(event.value) })

                }
            }

            is ItemPlaningEvent.SelectAllToListDayOfWeek -> {
                _state.update {

                    it.copy(listDayOfWeek = mutableListOf<Int>().apply { add(defaultAllDaysCode) })
                }
            }

            is ItemPlaningEvent.UnSelectAllToListDayOfWeek -> {
                _state.update {
                    it.copy(listDayOfWeek = it.listDayOfWeek.toMutableList().apply { clear() })
                }
            }

            is ItemPlaningEvent.UpdateEndDateUse -> {  _state.update { it.copy(endDateMedicineUse = event.value) }}
            is ItemPlaningEvent.UpdateStartDateUse -> {  _state.update { it.copy(startDateMedicineUse = event.value) }}
        }
    }


}

sealed class ItemPlaningEvent {
    data class UpdateMeasurementUnit(val measurementUnit: MeasurementUnit) : ItemPlaningEvent()
    data class UpdateNameMedicine(val nameMedicine: TextFieldValue) : ItemPlaningEvent()
    data class UpdateSelectedIfMedicationIsAsNeeded(val value: Boolean) : ItemPlaningEvent()
    data class UpdateReminderRestockMedicine(val value: Boolean) : ItemPlaningEvent()
    data class UpdateCurrentStocks(val value: Int) : ItemPlaningEvent()
    data class UpdateRemindMeOfStock(val value: Int) : ItemPlaningEvent()

    /// Список часових відміток за день
    data class AddToListTimesForDay(val value: MedicationDoseInfo) : ItemPlaningEvent()
    data class DeleteToListTimesForDay(val value: MedicationDoseInfo) : ItemPlaningEvent()

    /// Список днів за тиждень
    data class AddToListDayOfWeek(val value: Int) : ItemPlaningEvent()
    data class DeleteToListDayOfWeek(val value: Int) : ItemPlaningEvent()
    data class SelectAllToListDayOfWeek(val value: Int) : ItemPlaningEvent()
    data class UnSelectAllToListDayOfWeek(val value: Int) : ItemPlaningEvent()

    /// Період прийому ліків
    data class UpdateStartDateUse(val value: String) : ItemPlaningEvent()
    data class UpdateEndDateUse(val value: String) : ItemPlaningEvent()

}
