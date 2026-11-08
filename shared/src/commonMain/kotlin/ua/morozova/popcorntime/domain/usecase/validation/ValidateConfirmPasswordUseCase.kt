package ua.morozova.popcorntime.domain.usecase.validation

import ua.morozova.popcorntime.domain.ValidationError

class ValidateConfirmPasswordUseCase {
    operator fun invoke(password: String, confirmPassword: String): ValidationError? = when {
        confirmPassword.isEmpty() -> ValidationError.ConfirmPasswordEmpty
        confirmPassword != password -> ValidationError.PasswordsDoNotMatch
        else -> null
    }
}