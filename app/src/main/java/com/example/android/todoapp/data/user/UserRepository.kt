

package com.example.android.todoapp.data.user

import kotlinx.coroutines.flow.Flow

/**
 * Interface to the data layer.
 */
interface UserRepository {

    suspend fun getUser(): Flow<User?>

    suspend fun updateUser(username: String, nickname: String, description: String)

    suspend fun refreshUser()

    suspend fun clear()

}
