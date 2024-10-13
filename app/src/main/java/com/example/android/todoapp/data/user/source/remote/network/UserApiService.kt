package com.example.android.todoapp.data.user.source.remote.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class UserApiService @Inject constructor(
    private val httpClient: HttpClient
) {

    suspend fun getUserDetails(userId: String): RemoteUser {
        return httpClient.get("/users/$userId"){
            parameter("key1", "value1")
        }.body()  // Deserialize response to RemoteUser
    }

    suspend fun createUser(user: RemoteUser): RemoteUser {
        return httpClient.post("/users") {  // Relative URL
            setBody(user)  // Serialize RemoteUser object to JSON
        }.body()  // Deserialize response to RemoteUser
    }

    suspend fun updateUser(userId: String, user: RemoteUser): RemoteUser {
        return httpClient.put("/users/$userId") {  // Relative URL
            setBody(user)  // Serialize RemoteUser object to JSON
        }.body()  // Deserialize response to RemoteUser
    }

    suspend fun deleteUser(userId: String) {
        httpClient.delete("/users/$userId")
    }
}
