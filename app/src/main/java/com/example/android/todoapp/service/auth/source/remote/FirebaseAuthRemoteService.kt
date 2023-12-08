package com.example.android.todoapp.service.auth.source.remote

import com.example.android.todoapp.service.auth.AppAuthService
import com.example.android.todoapp.service.auth.source.AppAuthSession
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class FirebaseAuthRemoteService @Inject constructor() : AppAuthService {

    // A mutex is used to ensure that reads and writes are thread-safe.
    private val accessMutex = Mutex()
    private val mockSession = AppAuthSession("", "")

    override suspend fun login(username: String, password: String) : AppAuthSession {
        accessMutex.withLock {
            delay(0)
            return mockSession
        }
    }

    override suspend fun register(username: String, password: String) : AppAuthSession {
        accessMutex.withLock {
            delay(0)
            return mockSession
        }
    }

    override suspend fun logout() {

    }

    override suspend fun isLoggedIn(): Flow<Boolean> {
        TODO("Not implemented")
    }
}