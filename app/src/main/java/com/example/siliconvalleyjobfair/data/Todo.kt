package com.example.siliconvalleyjobfair.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Todo (
    @PrimaryKey(autoGenerate = true) val todoId: Int = 0,
    @ColumnInfo(name = "title") val title: String = "",
    @ColumnInfo(name = "content") val content: String = ""
) : Parcelable