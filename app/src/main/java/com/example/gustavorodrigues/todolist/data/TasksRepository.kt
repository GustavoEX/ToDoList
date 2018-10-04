package com.example.gustavorodrigues.todolist.data

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask


class TasksRepository(application: Application) {
    private val db : TaskRoomDatabase? = TaskRoomDatabase.getInstance(application)
    private val mTaskDao = db!!.taskDao()
    private val mAllTasks: LiveData<List<Task>> = mTaskDao.getAllTasks()

    fun getAllTasks(): LiveData<List<Task>> {
        return mAllTasks
    }

    fun insertTask(task: Task) {
        InsertAsyncTask(mTaskDao).execute(task)
    }

    fun deleteTask(task: Task) {
        deleteAsyncTask(mTaskDao).execute(task)
    }

    fun deleteAllTasks() {
        DeleteAllAsyncTask(mTaskDao).execute()
    }

    fun updateTask(task: Task) {
        UpdateAsyncTask(mTaskDao).execute(task)
    }

    private class InsertAsyncTask internal constructor(private val mAsyncTaskDao: TaskDao)
        : AsyncTask<Task, Void, Void>() {

        override fun doInBackground(vararg params: Task): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }

    private class deleteAsyncTask internal constructor(private val mAsyncTaskDao: TaskDao)
        : AsyncTask<Task, Void, Void>() {
        override fun doInBackground(vararg params: Task): Void? {
            mAsyncTaskDao.delete(params[0])
            return null
        }
    }

    private class UpdateAsyncTask internal constructor(private val mAsyncTaskDao: TaskDao)
        : AsyncTask<Task, Void, Void>() {
        override fun doInBackground(vararg params: Task): Void? {
            mAsyncTaskDao.update(params[0])
            return null
        }
    }

    private class DeleteAllAsyncTask internal constructor(private val mAsyncTaskDao: TaskDao)
        : AsyncTask<Void,Void,Void>() {
        override fun doInBackground(vararg params: Void?): Void? {
            mAsyncTaskDao.deleteAll()
            return null
        }
    }
}