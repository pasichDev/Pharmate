package com.pasichdev.pharmate.utils

import java.time.LocalTime
import java.time.format.DateTimeFormatter


fun formattedTime(hour: Int, minute: Int): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    val time = LocalTime.of(hour, minute).format(formatter)
    return time
}
