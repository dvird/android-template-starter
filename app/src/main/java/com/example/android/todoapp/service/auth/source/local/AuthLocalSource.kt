package com.example.android.todoapp.service.auth.source.local

import com.example.android.todoapp.service.auth.source.AppAuthSession
import kotlinx.coroutines.flow.Flow

interface AuthLocalSource {
    suspend fun clean()
    suspend fun isLoggedIn(): Flow<Boolean>
    suspend fun save(appAuthSession: AppAuthSession?)
}
