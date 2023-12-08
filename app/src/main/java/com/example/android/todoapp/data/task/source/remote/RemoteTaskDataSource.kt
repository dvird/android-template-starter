

package com.example.android.todoapp.data.task.source.remote

/**
 * Main entry point for accessing tasks data from the network.
 *
 */
interface RemoteTaskDataSource {

    suspend fun loadTasks(): List<NetworkTask>

    suspend fun saveTasks(tasks: List<NetworkTask>)
}
