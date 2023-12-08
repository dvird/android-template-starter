

package com.example.android.todoapp.data.task.source.remote

import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class DefaultRemoteTaskDataSource @Inject constructor() : RemoteTaskDataSource {

    // A mutex is used to ensure that reads and writes are thread-safe.
    private val accessMutex = Mutex()
    private var tasks = listOf(
        NetworkTask(
            id = "PISA",
            title = "Build tower in Pisa",
            shortDescription = "Ground looks good, no foundation work required."
        ),
        NetworkTask(
            id = "TACOMA",
            title = "Finish bridge in Tacoma",
            shortDescription = "Found awesome girders at half the cost!"
        )
    )

    override suspend fun loadTasks(): List<NetworkTask> = accessMutex.withLock {
        delay(SERVICE_LATENCY_IN_MILLIS)
        return tasks
    }

    override suspend fun saveTasks(newTasks: List<NetworkTask>) = accessMutex.withLock {
        delay(SERVICE_LATENCY_IN_MILLIS)
        tasks = newTasks
    }
}

private const val SERVICE_LATENCY_IN_MILLIS = 2000L
