package com.example.android.todoapp.data.user.source

import com.example.android.todoapp.data.user.User
import com.example.android.todoapp.data.user.source.local.LocalUser
import com.example.android.todoapp.data.user.source.remote.network.RemoteUser

/**
 * Data model mapping extension functions. There are three model types:
 *
 * - User (External): Model exposed to other layers in the architecture.
 * - LocalUser: Model used to represent a user stored locally in a database.
 * - RemoteUser: Model used to represent a user from the network.
 */

// --------------------------
// External (User) to Local (LocalUser)
// --------------------------

fun User.toLocal() = LocalUser(
    id = id,
    title = title,
    description = description,
)

fun List<User>.toLocal() = map(User::toLocal)

// --------------------------
// Local (LocalUser) to External (User)
// --------------------------

fun LocalUser.toExternal() = User(
    id = id,
    title = title,
    description = description,
)

@JvmName("localToExternal")
fun List<LocalUser>.toExternal() = map(LocalUser::toExternal)

// --------------------------
// Network (RemoteUser) to Local (LocalUser)
// --------------------------

fun RemoteUser.toLocal() = LocalUser(
    id = id,
    title = title,
    description = shortDescription,
)

@JvmName("networkToLocal")
fun List<RemoteUser>.toLocal() = map(RemoteUser::toLocal)

// --------------------------
// Local (LocalUser) to Network (RemoteUser)
// --------------------------

fun LocalUser.toNetwork() = RemoteUser(
    id = id,
    title = title,
    shortDescription = description,
)

fun List<LocalUser>.toNetwork() = map(LocalUser::toNetwork)

// --------------------------
// External (User) to Network (RemoteUser)
// --------------------------

fun User.toNetwork() = toLocal().toNetwork()

@JvmName("externalToNetwork")
fun List<User>.toNetwork() = map(User::toNetwork)

// --------------------------
// Network (RemoteUser) to External (User)
// --------------------------

fun RemoteUser.toExternal() = toLocal().toExternal()

@JvmName("networkToExternal")
fun List<RemoteUser>.toExternal() = map(RemoteUser::toExternal)
