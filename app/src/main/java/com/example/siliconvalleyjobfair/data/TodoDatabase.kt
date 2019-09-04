package com.example.siliconvalleyjobfair.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Todo::class), version = 1)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun getTodoDao(): TodoDao

    companion object {
        private var INSTANCE: TodoDatabase? = null

        fun getInstance(context: Context): TodoDatabase {
            if (INSTANCE == null) {
                synchronized(TodoDatabase::class) {
                    INSTANCE =
                        Room.databaseBuilder(context, TodoDatabase::class.java, "todo.db").build()
                }
            }
            return INSTANCE!!
        }
    }
}