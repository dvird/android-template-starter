

package com.example.android.todoapp.data.user.source.local

import kotlinx.coroutines.flow.Flow

/**
 * Main entry point for accessing tasks data from the network.
 *
 */
interface LocalUserDataSource {
    suspend fun save(localUser: LocalUser)

    suspend fun get() : Flow<LocalUser>

    suspend fun clear()
}
