package ua.morozova.popcorntime.ui.auth

import ua.morozova.popcorntime.domain.ValidationError
import ua.morozova.popcorntime.domain.auth.AuthError

object AuthContract {

    data class State(
        val username: String = "",
        val password: String = "",
        val usernameError: ValidationError? = null,
        val passwordError: ValidationError? = null,
        val isPasswordVisible: Boolean = false,
        val isLoading: Boolean = false
    )

    sealed interface Intent {
        data class UsernameChanged(val value: String) : Intent
        data class PasswordChanged(val value: String) : Intent
        data object TogglePasswordVisibility : Intent
        data object SignInClicked : Intent
        data object SignUpClicked : Intent
        data object BackClicked : Intent
    }

    sealed interface Effect {
        data object NavigateToHome : Effect
        data object NavigateToRegister : Effect
        data object NavigateBack : Effect
        data class ShowError(val error: AuthError) : Effect
    }
}