

package com.example.android.todoapp.data.source.network

import com.example.android.todoapp.data.task.source.remote.RemoteTaskDataSource
import com.example.android.todoapp.data.task.source.remote.NetworkTask

class FakeRemoteTaskDataSource(
    var tasks: MutableList<NetworkTask>? = mutableListOf()
) : RemoteTaskDataSource {
    override suspend fun loadTasks() = tasks ?: throw Exception("Task list is null")

    override suspend fun saveTasks(tasks: List<NetworkTask>) {
        this.tasks = tasks.toMutableList()
    }
}
