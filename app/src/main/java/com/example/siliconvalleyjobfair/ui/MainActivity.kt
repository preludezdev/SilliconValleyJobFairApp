package com.example.siliconvalleyjobfair.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.siliconvalleyjobfair.R
import com.example.siliconvalleyjobfair.data.Todo
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val todoRvAdapter = TodoRvAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.apply {
            adapter = todoRvAdapter
        }

        todoRvAdapter.addItem(Todo("1","1"))

    }
}
