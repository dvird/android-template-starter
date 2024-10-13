package com.example.android.todoapp.data.user.source.remote

import com.example.android.todoapp.data.user.User
import com.example.android.todoapp.data.user.source.remote.network.UserApiService
import com.example.android.todoapp.data.user.source.toExternal
import com.example.android.todoapp.data.user.source.toNetwork
import javax.inject.Inject

class DefaultRemoteUserDataSource @Inject constructor(
    private val userApiService: UserApiService
) : RemoteUserDataSource {

    override suspend fun save(user: User) {
        userApiService.updateUser(user.id, user.toNetwork())
    }

    override suspend fun get(): User {
        return userApiService.getUserDetails("").toExternal()
    }
}