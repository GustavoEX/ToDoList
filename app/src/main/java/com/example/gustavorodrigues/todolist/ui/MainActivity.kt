package com.example.gustavorodrigues.todolist.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import com.example.gustavorodrigues.todolist.R
import com.example.gustavorodrigues.todolist.viewmodel.TaskViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var taskViewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.addItemDecoration(DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL))
        recyclerview.setHasFixedSize(true)

        taskViewModel = ViewModelProviders.of(this)
                .get(TaskViewModel::class.java)

        val adapter = TaskViewAdapter(this,  taskViewModel)

        taskViewModel.getAllTasks().observe(this, Observer { tasks ->
            if (tasks != null) {
                adapter.setTasks(tasks)

            }
        })

        recyclerview.adapter = adapter


        fab.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        })

    }
}
