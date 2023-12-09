

package com.example.android.todoapp.data.source.local

import com.example.android.todoapp.data.task.source.local.LocalTask
import com.example.android.todoapp.data.task.source.local.TaskDao
import kotlinx.coroutines.flow.Flow

class FakeTaskDao(initialTasks: List<LocalTask>? = emptyList()) : TaskDao {

    private var _tasks: MutableMap<String, LocalTask>? = null

    var tasks: List<LocalTask>?
        get() = _tasks?.values?.toList()
        set(newTasks) {
            _tasks = newTasks?.associateBy { it.id }?.toMutableMap()
        }

    init {
        tasks = initialTasks
    }

    override suspend fun getAll() = tasks ?: throw Exception("Task list is null")

    override suspend fun getById(taskId: String): LocalTask? = _tasks?.get(taskId)

    override suspend fun upsertAll(tasks: List<LocalTask>) {
        _tasks?.putAll(tasks.associateBy { it.id })
    }

    override suspend fun upsert(task: LocalTask) {
        _tasks?.put(task.id, task)
    }

    override suspend fun updateCompleted(taskId: String, completed: Boolean) {
        _tasks?.get(taskId)?.let { it.isCompleted = completed }
    }

    override suspend fun deleteAll() {
        _tasks?.clear()
    }

    override suspend fun deleteById(taskId: String): Int {
        return if (_tasks?.remove(taskId) == null) {
            0
        } else {
            1
        }
    }

    override suspend fun deleteCompleted(): Int {
        _tasks?.apply {
            val originalSize = size
            entries.removeIf { it.value.isCompleted }
            return originalSize - size
        }
        return 0
    }

    override fun observeAll(): Flow<List<LocalTask>> {
        TODO("Not implemented")
    }

    override fun observeById(taskId: String): Flow<LocalTask> {
        TODO("Not implemented")
    }
}
