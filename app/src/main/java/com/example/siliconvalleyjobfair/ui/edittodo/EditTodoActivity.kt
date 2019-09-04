package com.example.siliconvalleyjobfair.ui.edittodo

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.siliconvalleyjobfair.R
import com.example.siliconvalleyjobfair.data.Todo
import com.example.siliconvalleyjobfair.data.TodoDatabase
import kotlinx.android.synthetic.main.activity_edit_todo.*

class EditTodoActivity : AppCompatActivity() {

    private lateinit var prevTodo: Todo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_todo)

        initView()
        initEvent()
    }

    private fun initView() {
        prevTodo = intent.getParcelableExtra("todo")

        et_edit_title.setText(prevTodo.title)
        et_edit_content.setText(prevTodo.content)
    }

    private fun initEvent() {
        bt_edit_update.setOnClickListener {
            val updatedTodo = Todo(
                prevTodo.todoId,
                et_edit_title.text.toString(),
                et_edit_content.text.toString()
            )

            Thread(Runnable {
                TodoDatabase
                    .getInstance(this)
                    .getTodoDao()
                    .updateTodo(updatedTodo)
            }).start()

            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

}