

package com.example.android.todoapp.data.task.source.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the task table.
 */
@Dao
interface TaskDao {

    /**
     * Observes list of tasks.
     *
     * @return all tasks.
     */
    @Query("SELECT * FROM task")
    fun observeAll(): Flow<List<LocalTask>>

    /**
     * Observes a single task.
     *
     * @param taskId the task id.
     * @return the task with taskId.
     */
    @Query("SELECT * FROM task WHERE id = :taskId")
    fun observeById(taskId: String): Flow<LocalTask>

    /**
     * Select all tasks from the tasks table.
     *
     * @return all tasks.
     */
    @Query("SELECT * FROM task")
    suspend fun getAll(): List<LocalTask>

    /**
     * Select a task by id.
     *
     * @param taskId the task id.
     * @return the task with taskId.
     */
    @Query("SELECT * FROM task WHERE id = :taskId")
    suspend fun getById(taskId: String): LocalTask?

    /**
     * Insert or update a task in the database. If a task already exists, replace it.
     *
     * @param task the task to be inserted or updated.
     */
    @Upsert
    suspend fun upsert(task: LocalTask)

    /**
     * Insert or update tasks in the database. If a task already exists, replace it.
     *
     * @param tasks the tasks to be inserted or updated.
     */
    @Upsert
    suspend fun upsertAll(tasks: List<LocalTask>)

    /**
     * Update the complete status of a task
     *
     * @param taskId id of the task
     * @param completed status to be updated
     */
    @Query("UPDATE task SET isCompleted = :completed WHERE id = :taskId")
    suspend fun updateCompleted(taskId: String, completed: Boolean)

    /**
     * Delete a task by id.
     *
     * @return the number of tasks deleted. This should always be 1.
     */
    @Query("DELETE FROM task WHERE id = :taskId")
    suspend fun deleteById(taskId: String): Int

    /**
     * Delete all tasks.
     */
    @Query("DELETE FROM task")
    suspend fun deleteAll()

    /**
     * Delete all completed tasks from the table.
     *
     * @return the number of tasks deleted.
     */
    @Query("DELETE FROM task WHERE isCompleted = 1")
    suspend fun deleteCompleted(): Int
}
