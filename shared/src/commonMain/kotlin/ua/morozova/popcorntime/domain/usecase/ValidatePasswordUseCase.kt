package ua.morozova.popcorntime.domain.usecase

import ua.morozova.popcorntime.domain.ValidationError

class ValidatePasswordUseCase {
    operator fun invoke(password: String): ValidationError? = when {
        password.isEmpty() -> ValidationError.PasswordEmpty
        password.length < MIN_LENGTH -> ValidationError.PasswordTooShort
        password.none { it.isLetter() } -> ValidationError.PasswordNoLetter
        password.none { it.isDigit() } -> ValidationError.PasswordNoDigit
        else -> null
    }

    companion object {
        const val MIN_LENGTH = 6
    }
}