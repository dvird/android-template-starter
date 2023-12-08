package com.example.android.todoapp.service.auth

import com.example.android.todoapp.service.auth.source.AppAuthSession
import com.example.android.todoapp.service.auth.source.local.AuthLocalSource
import com.example.android.todoapp.service.auth.source.remote.AppAuthRemoteService
import com.example.android.todoapp.support.di.ApplicationScope
import com.example.android.todoapp.support.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultAppAuthService @Inject constructor(
    private val networkDataSource: AppAuthRemoteService,
    private val localDataSource: AuthLocalSource,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
    @ApplicationScope private val scope: CoroutineScope,
) : AppAuthService {

    override suspend fun login(username: String, password: String): AppAuthSession {
        return withContext(dispatcher) {
            val response = networkDataSource.login(username, password)
            localDataSource.save(response)
            return@withContext response
        }
    }

    override suspend fun register(username: String, password: String): AppAuthSession {
        return withContext(dispatcher) {
            val response = networkDataSource.register(username, password)
            localDataSource.save(response)
            return@withContext response
        }
    }

    override suspend fun logout() {
        withContext(dispatcher) {
            // Clear any stored authentication tokens or user data
            localDataSource.clean()
        }
    }

    override suspend fun isLoggedIn(): Flow<Boolean> {
        return localDataSource.isLoggedIn()
    }
}
