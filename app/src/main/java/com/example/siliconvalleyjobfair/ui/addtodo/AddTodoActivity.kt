package com.example.siliconvalleyjobfair.ui.addtodo

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.siliconvalleyjobfair.R
import com.example.siliconvalleyjobfair.data.Todo
import com.example.siliconvalleyjobfair.data.TodoDatabase
import kotlinx.android.synthetic.main.activity_add_todo.*

class AddTodoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_todo)

        bt_new_add.setOnClickListener {
            val todo = Todo(0, et_new_title.text.toString(), et_new_content.text.toString())

            val addRunnable = Runnable {
                TodoDatabase
                    .getInstance(this)
                    .getTodoDao()
                    .insertTodo(todo)
            }

            Thread(addRunnable).start()

            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

}