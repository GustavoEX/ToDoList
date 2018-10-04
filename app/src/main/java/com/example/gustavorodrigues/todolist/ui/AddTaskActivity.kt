package com.example.gustavorodrigues.todolist.ui

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.gustavorodrigues.todolist.R
import com.example.gustavorodrigues.todolist.data.Task
import com.example.gustavorodrigues.todolist.viewmodel.TaskViewModel
import kotlinx.android.synthetic.main.activity_add_task.*
import java.util.*

class AddTaskActivity : AppCompatActivity() {
    private var taskViewModel: TaskViewModel? = null
    private var mTask: Task? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        taskViewModel = ViewModelProviders.of(this)
                .get(TaskViewModel::class.java)

        getIncomingIntent()

        btn_add_task.setOnClickListener(View.OnClickListener {
            if (TextUtils.isEmpty(et_add_task_title.text)) {
                Toast.makeText(this,
                        R.string.title_error, Toast.LENGTH_LONG).show()
            } else {
                val taskTitle = et_add_task_title.text.toString()
                val taskDesc = et_add_task_description.text.toString()
                val date = Date()
                if (mTask == null) {
                    mTask = Task(taskTitle = taskTitle, taskDescription = taskDesc,
                            taskCreatedAt = date, taskUpdatedAt = null, taskDone = false)
                    taskViewModel!!.insert(mTask!!)
                } else {
                    mTask!!.taskTitle = taskTitle
                    mTask!!.taskDescription = taskDesc
                    mTask!!.taskUpdatedAt = date
                    taskViewModel!!.update(mTask!!)
                }
                finish()
            }
        })
    }

    private fun getIncomingIntent() {
        if (intent.hasExtra(getString(R.string.task_id_extra))
                && intent.hasExtra(getString(R.string.task_title_extra))
                && intent.hasExtra(getString(R.string.task_desc_extra))
                && intent.hasExtra(getString(R.string.task_created_extra))) {
            val taskId = intent.getIntExtra(getString(R.string.task_id_extra), -1)
            val taskTitle = intent.getStringExtra(getString(R.string.task_title_extra))
            val taskDesc = intent.getStringExtra(getString(R.string.task_desc_extra))
            val taskCreatedDate = intent.getLongExtra(getString(R.string.task_created_extra), 0)

            if (taskId != -1) {
                btn_add_task.setText(R.string.edit_task_btn)
                mTask = Task(taskId, taskTitle, taskDesc, Date(taskCreatedDate),
                        null, false)
                et_add_task_title.setText(taskTitle)
                et_add_task_description.setText(taskDesc)
            }
        }
    }
}
