

package com.example.android.todoapp.data.user.source.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.sync.Mutex
import javax.inject.Inject

class DefaultLocalUserDataSource @Inject constructor() : LocalUserDataSource {

    // A mutex is used to ensure that reads and writes are thread-safe.
    private val accessMutex = Mutex()

    override suspend fun save(localUser: LocalUser) {
        TODO("Not yet implemented")
    }

    override suspend fun get(): Flow<LocalUser> {
        TODO("Not yet implemented")
    }

    override suspend fun clear() {
        TODO("Not yet implemented")
    }
}

private const val SERVICE_LATENCY_IN_MILLIS = 2000L
