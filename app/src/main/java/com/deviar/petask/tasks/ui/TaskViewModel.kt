package com.deviar.petask.tasks.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deviar.petask.common.database.data.model.TaskModel
import com.deviar.petask.common.database.domain.usecase.task.GetTaskByDateUseCase
import com.deviar.petask.common.database.domain.usecase.task.InsertTaskUseCase
import com.deviar.petask.common.database.domain.usecase.task.UpdateTaskUseCase
import com.deviar.petask.tasks.util.TaskConstans
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val getTaskByDateUseCase: GetTaskByDateUseCase,
    private val insertTaskUseCase: InsertTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
): ViewModel() {
    private var _taskList: Flow<List<TaskModel?>>? = MutableStateFlow(arrayListOf())
    val taskSuccess = _taskList?.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        null,
    )


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

    fun updateTaskDataBase(task: TaskModel) {
        viewModelScope.launch {
            updateTaskUseCase(task)
        }
    }

    fun insertTaskDataBase(newTask: TaskModel) {
        viewModelScope.launch {
            insertTaskUseCase(newTask)
        }
    }

    fun getTasksByDate(selectedDate: Date) {
        viewModelScope.launch {
            _taskList = getTaskByDateUseCase(selectedDate)
        }
    }
}