package com.example.siliconvalleyjobfair.ui.addtodo

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
import kotlinx.android.synthetic.main.activity_add_todo.*
import java.util.*

class AddTodoActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var dayAdapter: ArrayAdapter<CharSequence>

    private val monthList = mutableListOf<CharSequence>()
    private val dayList = mutableListOf<CharSequence>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_todo)

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
    }

    private fun initAdapter() {
        for (num in 0..12) {
            monthList.add("${num}월")
        }

        for (num in 0..31) {
            dayList.add("${num}일")
        }

        sp_new_month.adapter =
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, monthList)

        dayAdapter =
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, dayList)
        sp_new_day.adapter = dayAdapter
    }

    private fun initEvent() {
        sp_new_month.onItemSelectedListener = this

        bt_new_add.setOnClickListener {

            val title = et_new_title.text.toString()
            val content = et_new_content.text.toString()

            if (title.isEmpty() || content.isEmpty()) {
                Toast.makeText(this, "제목과 내용을 적어주세요", Toast.LENGTH_SHORT).show()
            } else {
                val todo = Todo(
                    0,
                    title,
                    content,
                    sp_new_month.selectedItemPosition,
                    sp_new_day.selectedItemPosition
                )

                Thread(Runnable {
                    TodoDatabase
                        .getInstance(this)
                        .getTodoDao()
                        .insertTodo(todo)

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