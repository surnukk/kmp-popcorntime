package ua.morozova.popcorntime.domain.usecase

import ua.morozova.popcorntime.domain.ValidationError

class ValidateEmailUseCase {
    operator fun invoke(email: String): ValidationError? {
        if (email.isBlank()) return ValidationError.EmailEmpty

        val at = email.indexOf('@')
        val domain = email.substringAfter('@', "")
        val isValid = !email.contains(' ') &&
                at > 0 &&
                at == email.lastIndexOf('@') &&
                domain.contains('.') &&
                !domain.startsWith('.') &&
                !domain.endsWith('.')

        return if (isValid) null else ValidationError.EmailInvalid
    }
}
