package com.deviar.petask.tasks.ui

import androidx.lifecycle.ViewModel
import com.deviar.petask.tasks.util.TaskConstans
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(): ViewModel() {
    // Obtener la fecha de hoy
    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(Date())
    }

    fun getUpcomingDates(): List<String> {
        val calendar = Calendar.getInstance()
        val datesList = mutableListOf<String>()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        datesList.add(dateFormat.format(calendar.time))

        for (i in TaskConstans.PRIMER_DIA_MOSTRAR ..TaskConstans.ULTIMO_DIA_MOSTRAR) {  // Obtener las próximas 5 fechas
            calendar.add(Calendar.DAY_OF_YEAR, 1)
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            datesList.add(dateFormat.format(calendar.time))
        }

        return datesList
    }
}