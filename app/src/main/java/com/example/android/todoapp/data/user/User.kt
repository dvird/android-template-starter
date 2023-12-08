

package com.example.android.todoapp.data.user

/**
 * Immutable model class for a Task.
 *
 * @param title title of the task
 * @param description description of the task
 * @param isCompleted whether or not this task is completed
 * @param id id of the task
 *
 * TODO: The constructor of this class should be `internal` but it is used in previews and tests
 *  so that's not possible until those previews/tests are refactored.
 */
data class User(
    val title: String = "",
    val description: String = "",
    val isCompleted: Boolean = false,
    val id: String,
) {

    val titleForList: String
        get() = if (title.isNotEmpty()) title else description

    val isActive
        get() = !isCompleted

    val isEmpty
        get() = title.isEmpty() || description.isEmpty()
}
