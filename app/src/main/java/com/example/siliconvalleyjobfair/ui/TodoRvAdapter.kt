package com.example.siliconvalleyjobfair.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.siliconvalleyjobfair.R
import com.example.siliconvalleyjobfair.data.Todo
import kotlinx.android.synthetic.main.item_todo.view.*

class TodoRvAdapter : RecyclerView.Adapter<TodoRvAdapter.TodoViewHolder>() {

    private val data = mutableListOf<Todo>()

    fun addItem(item: Todo) {
        data.add(item)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder =
        TodoViewHolder(parent)


    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bindView(data[position])
    }

    inner class TodoViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_todo, parent, false
        )
    ) {
        private val tvTitle = itemView.tv_title
        private val btEdit = itemView.bt_edit
        private val btDelete = itemView.bt_delete

        init {
            btEdit.setOnClickListener {
               Log.d("test", "btEdit Clicked!!")
            }
            btDelete.setOnClickListener {
                Log.d("test", "btDelete Clicked!!")
            }
        }

        fun bindView(item: Todo) {
            tvTitle.text = item.title
        }

    }
}