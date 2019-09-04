package com.example.siliconvalleyjobfair.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.siliconvalleyjobfair.R
import com.example.siliconvalleyjobfair.data.Todo
import com.example.siliconvalleyjobfair.data.TodoDatabase
import com.example.siliconvalleyjobfair.ui.addtodo.AddTodoActivity
import com.example.siliconvalleyjobfair.ui.edittodo.EditTodoActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val todoRvAdapter = TodoRvAdapter(this) { editEvent(it) }
    private lateinit var db: TodoDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()
        initEvent()
        initDB()

    }

    private fun initRecyclerView() {
        recyclerView.apply {
            adapter = todoRvAdapter
        }
    }

    private fun initDB() {
        db = TodoDatabase.getInstance(this)
        loadDataFromDB()
    }

    private fun initEvent() {
        fab.setOnClickListener {
            startActivityForResult(Intent(this, AddTodoActivity::class.java), 1)
        }
    }

    private fun editEvent(todo: Todo) {
        startActivityForResult(Intent(this, EditTodoActivity::class.java).apply {
            putExtra("todo", todo)
        }, 1)
    }

    private fun loadDataFromDB() {
        Thread(Runnable {
            val items = db.getTodoDao().getAll()

            runOnUiThread { todoRvAdapter.setItems(items) }
        }).start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            loadDataFromDB()
        }
    }

}
