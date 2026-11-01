package ua.morozova.popcorntime.ui.register

import ua.morozova.popcorntime.domain.ValidationError

object RegisterContract {

    data class State(
        val username: String = "",
        val email: String = "",
        val password: String = "",
        val confirmPassword: String = "",
        val usernameError: ValidationError? = null,
        val emailError: ValidationError? = null,
        val passwordError: ValidationError? = null,
        val confirmPasswordError: ValidationError? = null,
        val isPasswordVisible: Boolean = false
    )

    sealed interface Intent {
        data class UsernameChanged(val value: String) : Intent
        data class EmailChanged(val value: String) : Intent
        data class PasswordChanged(val value: String) : Intent
        data class ConfirmPasswordChanged(val value: String) : Intent
        data object TogglePasswordVisibility : Intent
        data object RegisterClicked : Intent
        data object SignInClicked : Intent
        data object BackClicked : Intent
    }

    sealed interface Effect {
        data object NavigateBack : Effect
    }
}
