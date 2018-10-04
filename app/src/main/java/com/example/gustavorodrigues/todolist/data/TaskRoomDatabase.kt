package com.example.gustavorodrigues.todolist.data

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import android.os.AsyncTask
import com.example.gustavorodrigues.todolist.data.converters.dateConverter
import java.util.*

@Database(entities = [(Task::class)], version = 1, exportSchema = false)
@TypeConverters(dateConverter::class)
abstract class TaskRoomDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    private class PopulateTodoAsyncTask(db: TaskRoomDatabase) : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg params: Void): Void? {
            taskDao.insert(Task(taskTitle = "Populate On Create 1",
                    taskDescription = "Just to Show",
                    taskCreatedAt = Date(),
                    taskUpdatedAt = null,
                    taskDone = false))
            taskDao.insert(Task(taskTitle = "Populate On Create 2",
                    taskDescription = "Just to Show",
                    taskCreatedAt = Date(),
                    taskUpdatedAt = null,
                    taskDone = false))
            taskDao.insert(Task(taskTitle = "Populate On Create 3",
                    taskDescription = "Just to Show",
                    taskCreatedAt = Date(),
                    taskUpdatedAt = null,
                    taskDone = false))
            return null
        }

        private val taskDao: TaskDao = db.taskDao()
    }

    companion object {
        private var instance: TaskRoomDatabase? = null
        fun getInstance(context: Context): TaskRoomDatabase? {
            if (instance == null) {
                synchronized(TaskRoomDatabase::class) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            TaskRoomDatabase::class.java,
                            "TaskDatabase")
                            .fallbackToDestructiveMigration()
                            .addCallback(populateCallback)
                            .build()
                }
            }
            return instance
        }

        private val populateCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateTodoAsyncTask(instance!!).execute()
            }
        }
    }
}