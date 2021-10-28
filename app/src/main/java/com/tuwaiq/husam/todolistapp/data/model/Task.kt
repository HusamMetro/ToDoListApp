package com.tuwaiq.husam.todolistapp.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "task_table")
data class Task(
    var title: String,
    val startDate: String,
    var endDate: String,
    var description: String,
    var pastDueDate: Boolean = false,
    var checked: Boolean = false,
    var desCompleted: String = "",
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) : Parcelable
