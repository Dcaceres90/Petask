package com.deviar.petask.tasks

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class TaskViewModel {
    // Obtener la fecha de hoy
    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(Date())
    }

    fun getUpcomingDates(): List<String> {
        val calendar = Calendar.getInstance()
        val datesList = mutableListOf<String>()

        for (i in 1..5) {  // Obtener las próximas 5 fechas
            calendar.add(Calendar.DAY_OF_YEAR, 1)
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            datesList.add(dateFormat.format(calendar.time))
        }

        return datesList
    }
}