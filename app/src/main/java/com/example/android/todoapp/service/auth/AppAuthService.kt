package com.example.android.todoapp.service.auth

import com.example.android.todoapp.service.auth.source.AppAuthSession
import kotlinx.coroutines.flow.Flow

interface AppAuthService {
    /**
     * Logs in a user with the provided username and password.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     */
    suspend fun login(username: String, password: String) : AppAuthSession

    /**
     * Registers a new user with the provided details.
     *
     * @param username The username for the new user.
     * @param password The password for the new user.
     */
    suspend fun register(username: String, password: String) : AppAuthSession

    /**
     * Logs out the current user.
     */
    suspend fun logout()

    /**
     * Checks if a user is currently logged in.
     *
     * @return True if a user is logged in, false otherwise.
     */
    suspend fun isLoggedIn(): Flow<Boolean>
}
