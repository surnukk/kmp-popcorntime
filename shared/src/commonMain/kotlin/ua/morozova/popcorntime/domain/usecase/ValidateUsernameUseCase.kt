package ua.morozova.popcorntime.domain.usecase

import ua.morozova.popcorntime.domain.ValidationError

class ValidateUsernameUseCase {
    operator fun invoke(username: String): ValidationError? = when {
        username.isBlank() -> ValidationError.UsernameEmpty
        username.length < MIN_LENGTH -> ValidationError.UsernameTooShort
        !username.all { it.isLetterOrDigit() || it == '_' || it == '.' } ->
            ValidationError.UsernameInvalidChars
        else -> null
    }

    companion object {
        const val MIN_LENGTH = 3
    }
}