package com.tuwaiq.husam.todolistapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Task(
    var title: String,
    val startDate: String,
    val endDate: String,
    var description: String,
    var completed: Boolean = false,
    var desCompleted: String = ""
) : Parcelable
