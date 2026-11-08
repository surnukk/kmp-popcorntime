package ua.morozova.popcorntime.ui.register

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
import ua.morozova.popcorntime.domain.usecase.validation.ValidateConfirmPasswordUseCase
import ua.morozova.popcorntime.domain.usecase.validation.ValidateEmailUseCase
import ua.morozova.popcorntime.domain.usecase.validation.ValidatePasswordUseCase
import ua.morozova.popcorntime.domain.usecase.validation.ValidateUsernameUseCase
import ua.morozova.popcorntime.ui.register.RegisterContract.Effect
import ua.morozova.popcorntime.ui.register.RegisterContract.Intent
import ua.morozova.popcorntime.ui.register.RegisterContract.State

class RegisterViewModel(
    private val validateUsername: ValidateUsernameUseCase,
    private val validateEmail: ValidateEmailUseCase,
    private val validatePassword: ValidatePasswordUseCase,
    private val validateConfirmPassword: ValidateConfirmPasswordUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()

    private val _effect = Channel<Effect>(Channel.BUFFERED)
    val effect: Flow<Effect> = _effect.receiveAsFlow()

    fun onIntent(intent: Intent) {
        when (intent) {
            is Intent.UsernameChanged ->
                _state.update { it.copy(username = intent.value, usernameError = null) }

            is Intent.EmailChanged ->
                _state.update { it.copy(email = intent.value, emailError = null) }

            is Intent.PasswordChanged ->
                _state.update { it.copy(password = intent.value, passwordError = null) }

            is Intent.ConfirmPasswordChanged ->
                _state.update { it.copy(confirmPassword = intent.value, confirmPasswordError = null) }

            Intent.TogglePasswordVisibility ->
                _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }

            Intent.RegisterClicked -> register()

            Intent.SignInClicked, Intent.BackClicked ->
                viewModelScope.launch { _effect.send(Effect.NavigateBack) }
        }
    }

    private fun register() {
        val current = _state.value
        val usernameError = validateUsername(current.username)
        val emailError = validateEmail(current.email)
        val passwordError = validatePassword(current.password)
        val confirmError = validateConfirmPassword(current.password, current.confirmPassword)

        _state.update {
            it.copy(
                usernameError = usernameError,
                emailError = emailError,
                passwordError = passwordError,
                confirmPasswordError = confirmError
            )
        }
    }
}
