package com.example.gustavorodrigues.todolist.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.example.gustavorodrigues.todolist.data.Task
import com.example.gustavorodrigues.todolist.data.TasksRepository

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val mTasksRepository = TasksRepository(application)
    private val mAllTasks = mTasksRepository.getAllTasks()

    fun getAllTasks(): LiveData<List<Task>> {
        return mAllTasks
    }

    fun insert(task: Task) {
        mTasksRepository.insertTask(task)
    }

    fun delete(task: Task) {
        mTasksRepository.deleteTask(task)
    }

    fun deleteAll() {
        mTasksRepository.deleteAllTasks()
    }

    fun update(task: Task) {
        mTasksRepository.updateTask(task)
    }

}