package com.deviar.petask.tasks.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deviar.petask.common.database.data.model.TaskModel
import com.deviar.petask.common.database.domain.usecase.GetUIDUseCase
import com.deviar.petask.common.database.domain.usecase.task.GetTaskByDateUseCase
import com.deviar.petask.common.database.domain.usecase.task.InsertTaskUseCase
import com.deviar.petask.common.database.domain.usecase.task.UpdateTaskUseCase
import com.deviar.petask.common.utils.LevelDificult
import com.deviar.petask.tasks.domain.NewTaskFormState
import com.deviar.petask.tasks.domain.TasksState
import com.deviar.petask.tasks.util.TaskConstans
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
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
    private val getUIDUseCase: GetUIDUseCase,
): ViewModel() {
    private var _uiTasksState: MutableStateFlow<TasksState> = MutableStateFlow(TasksState())
    val uiTasksState: StateFlow<TasksState> = _uiTasksState.asStateFlow()

    fun setUiTaskState(taksState: TasksState) {
        _uiTasksState.value = taksState
    }

    fun updateScreenSelectedDate(selectedDate: String) {
        _uiTasksState.update { estadoActual ->
            estadoActual.copy(
                selectedDate = selectedDate
            )
        }
    }

    fun updateScreenTasks(taskList: List<TaskModel>) {
        _uiTasksState.update { estadoActual ->
            estadoActual.copy(
                taskList = taskList
            )
        }
    }

    fun updateScreenDatesList(datesUpcoming: List<String>) {
        _uiTasksState.update { estadoActual ->
            estadoActual.copy(
                datesUpcoming = datesUpcoming
            )
        }
    }

    private var _newTaskFormState: MutableStateFlow<NewTaskFormState> = MutableStateFlow(NewTaskFormState())
    val newTaskFormState: StateFlow<NewTaskFormState> = _newTaskFormState.asStateFlow()

    fun updateNewTaskFormScreenDateToDoString(selectedDate: String) {
        _newTaskFormState.update { estadoActual ->
            estadoActual.copy(
                dateToDoString = selectedDate
            )
        }
    }

    fun updateNewTaskFormScreenDateToDo(selectedDate: Date) {
        _newTaskFormState.update { estadoActual ->
            estadoActual.copy(
                dateToDo = selectedDate
            )
        }
    }

    fun updateTitleNewTaskFormScreen(title: String) {
        _newTaskFormState.update { estadoActual ->
            estadoActual.copy(
                title = title
            )
        }
    }

    fun updateLevelDificultNewTaskFormScreen(levelDificult: LevelDificult) {
        _newTaskFormState.update { estadoActual ->
            estadoActual.copy(
                levelDificult = levelDificult,
            )
        }
    }

    fun setNewTaskFormState(taksState: NewTaskFormState) {
        _newTaskFormState.value = taksState
    }

    private var _taskList: Flow<List<TaskModel>>? = MutableStateFlow(arrayListOf())
    val taskSuccess = _taskList?.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        null,
    )


    // Obtener la fecha de hoy

    fun getDateSelectedFormat(): String {
        return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
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

    fun setTasksShowDialog(showDialog: Boolean) {
        _newTaskFormState.update { estadoActual ->
            estadoActual.copy(
                showDialog = showDialog,
            )
        }
    }

   var uuidState: MutableLiveData<String> = MutableLiveData("")


    fun getUID() {
        viewModelScope.launch {
            taskSuccess?.collect {
                 uuidState.value = getUIDUseCase.invoke()
            }
        }
    }

    fun collectedSucessTask() {
        viewModelScope.launch {
            taskSuccess?.collect {
                if (it != null) {
                    updateScreenTasks(it)
                }
            }
        }
    }
}