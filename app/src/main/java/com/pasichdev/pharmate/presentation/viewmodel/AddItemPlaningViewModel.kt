package com.pasichdev.pharmate.presentation.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.pasichdev.pharmate.defaultAllDaysCode
import com.pasichdev.pharmate.domain.MeasurementUnit
import com.pasichdev.pharmate.domain.model.MedicationDoseInfo
import com.pasichdev.pharmate.presentation.mvi.AddItemPlanningState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class AddItemPlanningViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(AddItemPlanningState())
    val state: StateFlow<AddItemPlanningState> = _state.asStateFlow()


    fun onEvent(event: ItemPlanningEvent) {
        when (event) {
            is ItemPlanningEvent.UpdateMeasurementUnit -> {
                _state.update { it.copy(measurementUnit = event.measurementUnit) }
            }

            is ItemPlanningEvent.UpdateNameMedicine -> {
                _state.update { it.copy(nameMedicine = event.nameMedicine) }
            }

            is ItemPlanningEvent.UpdateSelectedIfMedicationIsAsNeeded -> {
                _state.update {
                    it.copy(
                        selectedIfMedicationIsAsNeeded = event.value,
                        shouldRemind = if(event.value) false else _state.value.shouldRemind
                    )
                }
            }

            is ItemPlanningEvent.UpdateReminderRestockMedicine -> {
                _state.update { it.copy(reminderRestockMedicine = event.value) }
            }

            is ItemPlanningEvent.UpdateCurrentStocks -> {
                _state.update { it.copy(currentStocks = event.value) }
            }

            is ItemPlanningEvent.UpdateRemindMeOfStock -> {
                _state.update { it.copy(remindMeOfStock = event.value) }
            }

            is ItemPlanningEvent.AddToListTimesForDay -> {
                _state.update {

                    it.copy(listTimesForDay = it.listTimesForDay.toMutableList().apply {
                        if (!contains(event.value)) {
                            add(event.value)
                        }
                    })
                }

            }

            is ItemPlanningEvent.DeleteToListTimesForDay -> {
                _state.update {
                    it.copy(listTimesForDay = it.listTimesForDay.toMutableList()
                        .apply { remove(event.value) })
                }
            }

            is ItemPlanningEvent.AddToListDayOfWeek -> {
                _state.update {

                    it.copy(listDayOfWeek = it.listDayOfWeek.toMutableList().apply {
                        if (!contains(event.value)) {
                            add(event.value)
                        }
                    })
                }
            }

            is ItemPlanningEvent.DeleteToListDayOfWeek -> {
                _state.update {
                    it.copy(listDayOfWeek = it.listDayOfWeek.toMutableList()
                        .apply { remove(event.value) })

                }
            }

            is ItemPlanningEvent.SelectAllToListDayOfWeek -> {
                _state.update {

                    it.copy(listDayOfWeek = mutableListOf<Int>().apply { add(defaultAllDaysCode) })
                }
            }

            is ItemPlanningEvent.UnSelectAllToListDayOfWeek -> {
                _state.update {
                    it.copy(listDayOfWeek = it.listDayOfWeek.toMutableList().apply { clear() })
                }
            }

            is ItemPlanningEvent.UpdateEndDateUse -> {
                _state.update { it.copy(endDateMedicineUse = event.value) }
            }

            is ItemPlanningEvent.UpdateStartDateUse -> {
                _state.update { it.copy(startDateMedicineUse = event.value) }
            }

            is ItemPlanningEvent.UpdateRemindShould -> {
                _state.update { it.copy(shouldRemind = event.value) }
            }
        }
    }


}

sealed class ItemPlanningEvent {
    data class UpdateMeasurementUnit(val measurementUnit: MeasurementUnit) : ItemPlanningEvent()
    data class UpdateNameMedicine(val nameMedicine: TextFieldValue) : ItemPlanningEvent()
    data class UpdateSelectedIfMedicationIsAsNeeded(val value: Boolean) : ItemPlanningEvent()
    data class UpdateReminderRestockMedicine(val value: Boolean) : ItemPlanningEvent()
    data class UpdateCurrentStocks(val value: Int) : ItemPlanningEvent()
    data class UpdateRemindMeOfStock(val value: Int) : ItemPlanningEvent()
    data class UpdateRemindShould(val value: Boolean) : ItemPlanningEvent()

    /// Список часових відміток за день
    data class AddToListTimesForDay(val value: MedicationDoseInfo) : ItemPlanningEvent()
    data class DeleteToListTimesForDay(val value: MedicationDoseInfo) : ItemPlanningEvent()

    /// Список днів за тиждень
    data class AddToListDayOfWeek(val value: Int) : ItemPlanningEvent()
    data class DeleteToListDayOfWeek(val value: Int) : ItemPlanningEvent()
    data class SelectAllToListDayOfWeek(val value: Int) : ItemPlanningEvent()
    data class UnSelectAllToListDayOfWeek(val value: Int) : ItemPlanningEvent()

    /// Період прийому ліків
    data class UpdateStartDateUse(val value: String) : ItemPlanningEvent()
    data class UpdateEndDateUse(val value: String) : ItemPlanningEvent()

}
