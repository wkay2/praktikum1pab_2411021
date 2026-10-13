package com.example.a2411021praktikum1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.a2411021praktikum1.data.local.AppDatabase
import com.example.a2411021praktikum1.data.local.Task
import com.example.a2411021praktikum1.repository.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TaskRepository
    val allTasks: StateFlow<List<Task>>

    init {
        val taskDao = AppDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(taskDao)
        allTasks = repository.allTasks.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    fun addTask(mataKuliah: String, judul: String, deskripsi: String, deadline: String, prioritas: String, status: String) {
        viewModelScope.launch {
            repository.insert(Task(mataKuliah = mataKuliah, judul = judul, deskripsi = deskripsi, deadline = deadline, prioritas = prioritas, status = status))
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            repository.update(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.delete(task)
        }
    }

    suspend fun getTaskById(id: Int): Task? {
        return repository.getTaskById(id)
    }
}