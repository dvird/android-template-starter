

package com.example.android.todoapp.data.user

import com.example.android.todoapp.data.user.source.local.LocalUserDataSource
import com.example.android.todoapp.data.user.source.remote.RemoteUserDataSource
import com.example.android.todoapp.data.user.source.toExternal
import com.example.android.todoapp.support.di.ApplicationScope
import com.example.android.todoapp.support.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Default implementation of [UserRepository]. Single entry point for managing user' data.
 *
 * @param remoteDataSource - The network data source
 * @param localDataSource - The local data source
 * @param dispatcher - The dispatcher to be used for long running or complex operations, such as ID
 * generation or mapping many models.
 * @param scope - The coroutine scope used for deferred jobs where the result isn't important, such
 * as sending data to the network.
 */
@Singleton
class DefaultUserRepository @Inject constructor(
    private val remoteDataSource: RemoteUserDataSource,
    private val localDataSource: LocalUserDataSource,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
    @ApplicationScope private val scope: CoroutineScope,
) : UserRepository {

    override suspend fun getUser(): Flow<User?> {
        return localDataSource.get().map { it.toExternal() }
    }

    override suspend fun updateUser(username: String, nickname: String, description: String) {

    }

    override suspend fun refreshUser() {
        withContext(dispatcher) {
            val remoteTasks = remoteDataSource.get()
            localDataSource.clear()
        }
    }

    override suspend fun clear() {
        localDataSource.clear()
    }
}
