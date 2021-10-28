package com.tuwaiq.husam.todolistapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Task(
    var title: String,
    val startDate: String,
    var endDate: String,
    var description: String,
    var pastDueDate: Boolean = false,
    var checked: Boolean = false,
    var desCompleted: String = ""
) : Parcelable
