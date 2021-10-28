package com.tuwaiq.husam.todolistapp.data

import androidx.room.*
import com.tuwaiq.husam.todolistapp.data.model.Task

@Dao
interface TaskDao {
    @Query("SELECT * FROM task_table")
    fun getAllTasks(): List<Task>

    @Insert
    fun insert(task: Task)

    @Update
    fun update(task: Task)

    @Delete
    fun delete(task: Task)
}