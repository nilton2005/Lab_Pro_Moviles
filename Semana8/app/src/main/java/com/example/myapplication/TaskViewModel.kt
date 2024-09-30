package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Task
import com.example.myapplication.data.TaskDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class TaskViewModel(private val dao: TaskDao): ViewModel() {
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks : StateFlow<List<Task>> = _tasks
    init {
        viewModelScope.launch {
            _tasks.value = dao.getAllTasks()
        }
    }
    fun addTask(description: String) {
        val newTasks = Task(description = description)
        viewModelScope.launch {
            dao.insertTask(newTasks)
            _tasks.value = dao.getAllTasks() // Actualiza la lista de tareas
        }
    }


    fun toggleTaskCompletion(task: Task) {
        viewModelScope.launch {
            val updatedTask = task.copy(isCompleted = !task.isCompleted)
            dao.updateTask(updatedTask)
            _tasks.value = dao.getAllTasks() // Actualiza la lista de tareas
        }
    }

    fun deleteAllTasks() {
        viewModelScope.launch {
            dao.deleteAllTasks()
            _tasks.value = emptyList() // Actualiza la lista de tareas
        }
    }
    // edit task
    fun updateTask(task: Task) = viewModelScope.launch {
        dao.updateTask(task)
        _tasks.value = dao.getAllTasks()
    }
    suspend fun getTaskById(taskId: Int): Task? {
        return withContext(Dispatchers.IO) {
            dao.getTaskById(taskId)
        }
    }

    fun deleteTask(task: Task)= viewModelScope.launch {
        withContext(Dispatchers.IO) {
            dao.deleteTask(task)
            _tasks.value = dao.getAllTasks()
        }

    }



}