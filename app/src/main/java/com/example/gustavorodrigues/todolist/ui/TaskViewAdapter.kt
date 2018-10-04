package com.example.gustavorodrigues.todolist.ui

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.example.gustavorodrigues.todolist.R
import com.example.gustavorodrigues.todolist.data.Task
import com.example.gustavorodrigues.todolist.viewmodel.TaskViewModel
import kotlinx.android.synthetic.main.recyclerview_task_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class TaskViewAdapter(context: Context, viewModel: TaskViewModel) : RecyclerView.Adapter<TaskViewAdapter.TaskViewHolder>() {
    private val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
    private var tasks: List<Task> = ArrayList()
    private val parentContext: Context = context
    private val taskViewModel = viewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_task_item, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentTask = tasks[position]
        holder.doneCheckbox.isChecked = currentTask.taskDone
        holder.doneCheckbox.setOnClickListener(View.OnClickListener {
            currentTask.taskDone = holder.doneCheckbox.isChecked
            taskViewModel.update(currentTask)
        })
        holder.titleTextView.text = currentTask.taskTitle
        holder.descriptionTextView.text = currentTask.taskDescription
        holder.updatedDateTextView.text = if (currentTask.taskUpdatedAt == null)
            sdf.format(currentTask.taskCreatedAt) else sdf.format(currentTask.taskUpdatedAt)
        holder.itemView.setOnLongClickListener(View.OnLongClickListener {
            val builder = AlertDialog.Builder(parentContext)
            builder.setMessage("Do you wish to delete this task?")
                    .setPositiveButton("Delete", DialogInterface.OnClickListener { _, _ ->
                        taskViewModel.delete(currentTask)
                    })
            val dialog = builder.create()
            dialog.show()
            return@OnLongClickListener true
        })
        holder.itemView.setOnClickListener(View.OnClickListener {
            parentContext.startActivity(setIntent(currentTask))
        })

    }

    private fun setIntent(currentTask: Task) : Intent {
        val intent = Intent(parentContext, AddTaskActivity::class.java)
        intent.putExtra("TaskId",
                currentTask.id)
        intent.putExtra("TaskTitle",
                currentTask.taskTitle)
        intent.putExtra("TaskDesc",
                currentTask.taskDescription)
        intent.putExtra("TaskCreated",
                currentTask.taskCreatedAt.time)
        return intent
    }

    fun setTasks(tasks: List<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }


    fun getTasks(): List<Task> {
        return tasks
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val titleTextView: TextView = itemView.text_view_title
        internal val descriptionTextView: TextView = itemView.text_view_description
        internal val updatedDateTextView: TextView = itemView.text_view_updated_date
        internal val doneCheckbox: CheckBox = itemView.checkbox_done

    }
}