package ua.morozova.popcorntime.domain

enum class ValidationError {
    UsernameEmpty,
    UsernameTooShort,
    UsernameInvalidChars,
    PasswordEmpty,
    PasswordTooShort,
    PasswordNoLetter,
    PasswordNoDigit,
    EmailEmpty,
    EmailInvalid,
    ConfirmPasswordEmpty,
    PasswordsDoNotMatch
}
