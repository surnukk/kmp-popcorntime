package ua.morozova.popcorntime.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ua.morozova.popcorntime.domain.usecase.ValidatePasswordUseCase
import ua.morozova.popcorntime.domain.usecase.ValidateUsernameUseCase
import ua.morozova.popcorntime.ui.auth.AuthContract.Effect
import ua.morozova.popcorntime.ui.auth.AuthContract.Intent
import ua.morozova.popcorntime.ui.auth.AuthContract.State

class AuthViewModel(
    private val validateUsername: ValidateUsernameUseCase,
    private val validatePassword: ValidatePasswordUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()

    private val _effect = Channel<Effect>(Channel.BUFFERED)
    val effect: Flow<Effect> = _effect.receiveAsFlow()

    fun onIntent(intent: Intent) {
        when (intent) {
            is Intent.UsernameChanged ->
                _state.update { it.copy(username = intent.value, usernameError = null) }

            is Intent.PasswordChanged ->
                _state.update { it.copy(password = intent.value, passwordError = null) }

            Intent.TogglePasswordVisibility ->
                _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }

            Intent.SignInClicked -> signIn()
            Intent.SignUpClicked -> sendEffect(Effect.NavigateToRegister)
            Intent.BackClicked -> sendEffect(Effect.NavigateBack)
        }
    }

    private fun signIn() {
        val current = _state.value
        val usernameError = validateUsername(current.username)
        val passwordError = validatePassword(current.password)

        _state.update { it.copy(usernameError = usernameError, passwordError = passwordError) }

        if (usernameError == null && passwordError == null) {
            sendEffect(Effect.NavigateToHome(current.username))
        }
    }

    private fun sendEffect(effect: Effect) {
        viewModelScope.launch { _effect.send(effect) }
    }
}