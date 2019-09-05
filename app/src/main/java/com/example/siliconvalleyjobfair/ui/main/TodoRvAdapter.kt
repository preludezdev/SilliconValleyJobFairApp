package com.example.siliconvalleyjobfair.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.siliconvalleyjobfair.R
import com.example.siliconvalleyjobfair.data.Todo
import com.example.siliconvalleyjobfair.data.TodoDatabase
import kotlinx.android.synthetic.main.item_todo.view.*

class TodoRvAdapter(
    private val context: Context,
    private val clickItemEvent: (str: String) -> Unit,
    private val clickEditEvent: (todo: Todo) -> Unit
) :
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

    fun swapItems(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                todoList[i] = todoList.set(i + 1, todoList[i])
            }
        } else {
            for (i in fromPosition..toPosition + 1) {
                todoList[i] = todoList.set(i - 1, todoList[i])
            }
        }

        notifyItemMoved(fromPosition, toPosition)
    }

    inner class TodoViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_todo, parent, false
        )
    ) {
        private val tvTitle = itemView.tv_title
        private val ivEdit = itemView.iv_edit
        private val ivDelete = itemView.iv_delete
        private val tvDeadline = itemView.tv_deadline

        init {
            tvTitle.setOnClickListener {
                clickItemEvent(todoList[adapterPosition].content)
            }

            ivEdit.setOnClickListener {
                clickEditEvent(todoList[adapterPosition])
            }

            ivDelete.setOnClickListener {
                deleteItem(adapterPosition)
            }
        }

        fun bindView(item: Todo) {
            tvTitle.text = item.title

            if (item.month != 0)
                tvDeadline.text = String.format("%d.%d", item.month, item.day)
        }
    }
}