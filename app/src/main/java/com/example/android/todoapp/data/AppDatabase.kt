

package com.example.android.todoapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.android.todoapp.data.task.source.local.LocalTask
import com.example.android.todoapp.data.task.source.local.TaskDao
import com.example.android.todoapp.data.user.source.local.LocalUser

/**
 * The Room Database that contains the Task table.
 * Note that exportSchema should be true in production databases.
 */
@Database(entities = [LocalTask::class, LocalUser::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}
