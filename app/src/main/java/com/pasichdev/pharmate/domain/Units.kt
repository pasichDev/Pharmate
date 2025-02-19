package com.pasichdev.pharmate.domain

data class MeasurementUnit(
    val fullName: String, val abbreviation: String
)

var defaultMeasurementUnit = MeasurementUnit("Міліграм(и)", "мг")

val measurementUnits = listOf(
    MeasurementUnit("Таблетка(и)", "таб."),
    MeasurementUnit("Мо(ї)", "Мо"),
    MeasurementUnit("Ампула(и)", "амп."),
    MeasurementUnit("Вагінальна капсула(и)", "ваг. капс."),
    MeasurementUnit("Вагінальний супозиторій(ї)", "ваг. суп."),
    MeasurementUnit("Вдих(и)", "вдх."),
    MeasurementUnit("Грам(и)", "г"),
    MeasurementUnit("Капсула(и)", "капс."),
    MeasurementUnit("Крапля(і)", "кр."),
    MeasurementUnit("Мікрограм(и)", "мкг"),
    MeasurementUnit("Міліметр(и)", "мм"),
    defaultMeasurementUnit,
    MeasurementUnit("Мілілітр(и)", "мл"),
    MeasurementUnit("Одиниця(і)", "од."),
    MeasurementUnit("Пакетик(и)", "пак."),
    MeasurementUnit("Пластир(і)", "пл."),
    MeasurementUnit("Порція(ії)", "порц."),
    MeasurementUnit("Пшик(и)", "пшк."),
    MeasurementUnit("Столова ложка(и)", "ст. л."),
    MeasurementUnit("Супозиторій(и)", "суп."),
    MeasurementUnit("Чайна ложка(и)", "ч. л."),
    MeasurementUnit("Штука(и)", "шт."),
    MeasurementUnit("Ін'єкція(ії)", "ін'єк.")

)
