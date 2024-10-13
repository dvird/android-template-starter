package com.example.android.todoapp.data.user.source.remote

import com.example.android.todoapp.data.user.User
import com.example.android.todoapp.data.user.source.local.LocalUser

/**
 * Main entry point for accessing tasks data from the network.
 *
 */
interface RemoteUserDataSource {
    suspend fun save(user: User)

    suspend fun get(): User
}
