package com.pasichdev.pharmate.presentation.mvi

import androidx.compose.ui.text.input.TextFieldValue
import com.pasichdev.pharmate.defaultAllDaysCode
import com.pasichdev.pharmate.domain.MeasurementUnit
import com.pasichdev.pharmate.domain.defaultMeasurementUnit
import com.pasichdev.pharmate.domain.model.MedicationDoseInfo


data class AddItemPlanningState(
    val nameMedicine: TextFieldValue = TextFieldValue("Ibuprofen"), // TODO CLEAN
    val measurementUnit: MeasurementUnit = defaultMeasurementUnit,
    val reminderRestockMedicine: Boolean = false,
    val currentStocks: Int = 30,
    val remindMeOfStock: Int = 10,
    val selectedIfMedicationIsAsNeeded: Boolean = false,
    val listTimesForDay: MutableList<MedicationDoseInfo> = mutableListOf<MedicationDoseInfo>().apply {
        add(MedicationDoseInfo(time = "08:00", dose = 1))
        add(MedicationDoseInfo(time = "12:00", dose = 1))
        add(MedicationDoseInfo(time = "15:00", dose = 1))
        add(MedicationDoseInfo(time = "20:00", dose = 1))

    }, // TODO CLEAN (EMPTY)
    /**
     * Зберігаються дні тижня лише за порядковим номером (0 = неділя), якщо елемент 100 то всі дні тижня (defaultAllDaysCode)
     */
    val listDayOfWeek: MutableList<Int> = mutableListOf<Int>().apply { add(defaultAllDaysCode) },
    val startDateMedicineUse: String = "",
    val endDateMedicineUse: String = "",
    val shouldRemind: Boolean = false,
)