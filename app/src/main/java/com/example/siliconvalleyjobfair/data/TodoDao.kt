package com.example.siliconvalleyjobfair.data

import androidx.room.*

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo")
    fun getAll(): List<Todo>

    @Query("SELECT * FROM todo WHERE todoId = :userId")
    fun getTodoById(userId: Int): Todo

    @Insert
    fun insertTodo(todo: Todo)

    @Update
    fun updateTodo(todo: Todo)

    @Delete
    fun deleteTodo(todo: Todo)
}