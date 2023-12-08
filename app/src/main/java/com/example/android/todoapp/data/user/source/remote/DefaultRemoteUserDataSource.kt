

package com.example.android.todoapp.data.user.source.remote

import com.example.android.todoapp.data.user.source.local.LocalUser
import kotlinx.coroutines.sync.Mutex
import javax.inject.Inject

class DefaultRemoteUserDataSource @Inject constructor() : RemoteUserDataSource {

    // A mutex is used to ensure that reads and writes are thread-safe.
    private val accessMutex = Mutex()

    override suspend fun save(localUser: LocalUser) {
        TODO("Not yet implemented")
    }

    override suspend fun get(): LocalUser {
        TODO("Not yet implemented")
    }
}

private const val SERVICE_LATENCY_IN_MILLIS = 2000L
