package com.example.siliconvalleyjobfair.ui.edittodo

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.siliconvalleyjobfair.R
import com.example.siliconvalleyjobfair.data.Todo
import com.example.siliconvalleyjobfair.data.TodoDatabase
import kotlinx.android.synthetic.main.activity_edit_todo.*
import java.util.*

class EditTodoActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var prevTodo: Todo
    private lateinit var dayAdapter: ArrayAdapter<CharSequence>

    private val monthList = mutableListOf<CharSequence>()
    private val dayList = mutableListOf<CharSequence>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_todo)

        initView()
        initEvent()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val cal = Calendar.getInstance().apply {
            set(Calendar.MONTH, position)
        }

        initDaySpinner(cal.getActualMaximum(Calendar.DAY_OF_MONTH))
    }

    private fun initView() {
        initAdapter()

        prevTodo = intent.getParcelableExtra("todo")

        et_edit_title.setText(prevTodo.title)
        et_edit_title.setSelection(prevTodo.title.length)
        et_edit_content.setText(prevTodo.content)
        et_edit_content.setSelection(prevTodo.content.length)
        sp_edit_month.setSelection(prevTodo.month)
        sp_edit_day.setSelection(prevTodo.day)
    }

    private fun initAdapter() {
        for (num in 0..12) {
            monthList.add("${num}월")
        }

        for (num in 0..31) {
            dayList.add("${num}일")
        }

        sp_edit_month.adapter =
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, monthList)

        dayAdapter =
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, dayList)
        sp_edit_day.adapter = dayAdapter
    }

    private fun initEvent() {
        bt_edit_update.setOnClickListener {
            val title = et_edit_title.text.toString()
            val content = et_edit_content.text.toString()

            if (title.isEmpty() || content.isEmpty()) {
                Toast.makeText(this, "제목과 내용을 적어주세요", Toast.LENGTH_SHORT).show()
            } else {
                val updatedTodo = Todo(
                    prevTodo.todoId,
                    title,
                    content,
                    sp_edit_month.selectedItemPosition,
                    sp_edit_day.selectedItemPosition
                )

                Thread(Runnable {
                    TodoDatabase
                        .getInstance(this)
                        .getTodoDao()
                        .updateTodo(updatedTodo)

                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }).start()
            }
        }
    }

    private fun initDaySpinner(maxDay: Int) {
        dayList.clear()

        for (num in 0..maxDay) {
            dayList.add("${num}일")
        }

        dayAdapter.notifyDataSetChanged()
    }

}