package com.tuwaiq.husam.todolistapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Task(val title: String, val startDate: String, val endDate: String) : Parcelable
