/*
 * Copyright 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.todoapp.data.user.source

import com.example.android.todoapp.data.user.User
import com.example.android.todoapp.data.user.source.local.LocalUser
import com.example.android.todoapp.data.user.source.remote.RemoteUser
import com.example.android.todoapp.data.user.source.remote.TaskStatus

/**
 * Data model mapping extension functions. There are three model types:
 *
 * - Task: External model exposed to other layers in the architecture.
 * Obtained using `toExternal`.
 *
 * - NetworkTask: Internal model used to represent a task from the network. Obtained using
 * `toNetwork`.
 *
 * - LocalTask: Internal model used to represent a task stored locally in a database. Obtained
 * using `toLocal`.
 *
 */

// External to local
fun User.toLocal() = LocalUser(
    id = id,
    title = title,
    description = description,
    isCompleted = isCompleted,
)

fun List<User>.toLocal() = map(User::toLocal)

// Local to External
fun LocalUser.toExternal() = User(
    id = id,
    title = title,
    description = description,
    isCompleted = isCompleted,
)

// Note: JvmName is used to provide a unique name for each extension function with the same name.
// Without this, type erasure will cause compiler errors because these methods will have the same
// signature on the JVM.
@JvmName("localToExternal")
fun List<LocalUser>.toExternal() = map(LocalUser::toExternal)

// Network to Local
fun RemoteUser.toLocal() = LocalUser(
    id = id,
    title = title,
    description = shortDescription,
    isCompleted = (status == TaskStatus.COMPLETE),
)

@JvmName("networkToLocal")
fun List<RemoteUser>.toLocal() = map(RemoteUser::toLocal)

// Local to Network
fun LocalUser.toNetwork() = RemoteUser(
    id = id,
    title = title,
    shortDescription = description,
    status = if (isCompleted) { TaskStatus.COMPLETE } else { TaskStatus.ACTIVE }
)

fun List<LocalUser>.toNetwork() = map(LocalUser::toNetwork)

// External to Network
fun User.toNetwork() = toLocal().toNetwork()

@JvmName("externalToNetwork")
fun List<User>.toNetwork() = map(User::toNetwork)

// Network to External
fun RemoteUser.toExternal() = toLocal().toExternal()

@JvmName("networkToExternal")
fun List<RemoteUser>.toExternal() = map(RemoteUser::toExternal)
