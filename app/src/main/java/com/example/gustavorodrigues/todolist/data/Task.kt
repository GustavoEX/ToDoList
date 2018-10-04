package com.example.gustavorodrigues.todolist.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*
@Entity(tableName = "task_table")
data class Task(
                @PrimaryKey(autoGenerate = true)
                var id : Int = 0,
                @ColumnInfo(name = "title")
                var taskTitle : String,
                @ColumnInfo(name = "description")
                var taskDescription: String,
                @ColumnInfo(name = "createdAt")
                val taskCreatedAt: Date,
                @ColumnInfo(name = "updatedAt")
                var taskUpdatedAt: Date?,
                @ColumnInfo(name = "finished")
                var taskDone: Boolean)