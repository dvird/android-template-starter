package com.example.android.todoapp.data.user.source.remote.network

/**
 * Internal model used to represent a task obtained from the network. This is used inside the data
 * layer only.
 *
 * See ModelMappingExt.kt for mapping functions used to convert this model to other
 * models.
 */
data class RemoteUser(
    val id: String,
    val title: String,
    val shortDescription: String,
)
