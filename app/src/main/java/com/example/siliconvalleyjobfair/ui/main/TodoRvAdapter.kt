package com.example.siliconvalleyjobfair.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.siliconvalleyjobfair.R
import com.example.siliconvalleyjobfair.data.Todo
import com.example.siliconvalleyjobfair.data.TodoDatabase
import kotlinx.android.synthetic.main.item_todo.view.*

class TodoRvAdapter(private val context: Context, private val clickEvent: (todo: Todo) -> Unit) :
    RecyclerView.Adapter<TodoRvAdapter.TodoViewHolder>() {

    private val todoList = mutableListOf<Todo>()

    fun setItems(items: List<Todo>) {
        todoList.clear()
        todoList.addAll(items)
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        val deletedTodo = todoList.removeAt(position)

        Thread(Runnable {
            TodoDatabase.getInstance(context).getTodoDao().deleteTodo(deletedTodo)
        }).start()

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder =
        TodoViewHolder(parent)


    override fun getItemCount(): Int = todoList.size

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bindView(todoList[position])
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
                clickEvent(todoList[adapterPosition])
            }

            btDelete.setOnClickListener {
                deleteItem(adapterPosition)
            }
        }

        fun bindView(item: Todo) {
            tvTitle.text = item.title
        }

    }
}