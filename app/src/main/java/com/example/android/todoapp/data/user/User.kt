package com.example.android.todoapp.data.user

/**
 * Immutable model class for a Task.
 *
 * @param title title of the task
 * @param description description of the task
 * @param id id of the task
 *
 * TODO: The constructor of this class should be `internal` but it is used in previews and tests
 *  so that's not possible until those previews/tests are refactored.
 */
data class User(
    val title: String = "",
    val description: String = "",
    val id: String,
) {

    val titleForList: String
        get() = if (title.isNotEmpty()) title else description

    val isEmpty
        get() = title.isEmpty() || description.isEmpty()
}
