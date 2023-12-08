package com.example.android.todoapp.service.auth.source.local

import com.example.android.todoapp.service.auth.source.AppAuthSession
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import javax.inject.Inject

class DefaultAuthLocalSource @Inject constructor() : AuthLocalSource {

    // A mutex is used to ensure that reads and writes are thread-safe.
    private val accessMutex = Mutex()

    private var appAuthSession: AppAuthSession? = null

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    override suspend fun clean() {
        appAuthSession = null
    }

    override suspend fun isLoggedIn(): Flow<Boolean> {
        return isLoggedIn
    }

    override suspend fun save(appAuthSession: AppAuthSession?) {
        this.appAuthSession = appAuthSession
        _isLoggedIn.emit(appAuthSession != null)
    }


}

private const val SERVICE_LATENCY_IN_MILLIS = 2000L
