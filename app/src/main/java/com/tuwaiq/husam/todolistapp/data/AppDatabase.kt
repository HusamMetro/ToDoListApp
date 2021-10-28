package com.tuwaiq.husam.todolistapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tuwaiq.husam.todolistapp.data.model.Task

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val taskDao: TaskDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getAppDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
            }
            return INSTANCE
        }
    }
}