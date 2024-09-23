

package com.example.android.todoapp.module.intro.login

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.todoapp.R
import com.example.android.todoapp.service.auth.AppAuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * UiState for the login screen
 */
data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val userMessage: Int? = null,
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
)

/**
 * ViewModel for the login screen.
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authService: AppAuthService,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // A MutableStateFlow needs to be created in this ViewModel. The source of truth of the current
    // editable Task is the ViewModel, we need to mutate the UI state directly in methods such as
    // `updateTitle` or `updateDescription`
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()


    // Called when clicking on fab.
    fun login() {
        val username = uiState.value.username
        val password = uiState.value.password
        if (username.isEmpty() || password.isEmpty()) {
            _uiState.update {
                it.copy(userMessage = R.string.empty_details_message)
            }
            return
        }
        viewModelScope.launch {
            authService.login(username = username, password = password)
            /** screen will be replaced from [MainNavController.kt] **/
        }
    }

    fun snackbarMessageShown() {
        _uiState.update {
            it.copy(userMessage = null)
        }
    }

    fun updateUsername(username: String) {
        _uiState.update {
            it.copy(username = username)
        }
    }

    fun updatePassword(password: String) {
        _uiState.update {
            it.copy(password = password)
        }
    }
}
