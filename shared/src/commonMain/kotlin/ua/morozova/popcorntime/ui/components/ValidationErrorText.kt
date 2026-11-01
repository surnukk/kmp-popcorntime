package ua.morozova.popcorntime.ui.components

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource
import popcorntime.shared.generated.resources.*
import ua.morozova.popcorntime.domain.ValidationError

@Composable
fun ValidationError.message(): String = stringResource(
    when (this) {
        ValidationError.UsernameEmpty -> Res.string.error_username_empty
        ValidationError.UsernameTooShort -> Res.string.error_username_short
        ValidationError.UsernameInvalidChars -> Res.string.error_username_chars
        ValidationError.PasswordEmpty -> Res.string.error_password_empty
        ValidationError.PasswordTooShort -> Res.string.error_password_short
        ValidationError.PasswordNoLetter -> Res.string.error_password_letter
        ValidationError.PasswordNoDigit -> Res.string.error_password_digit
        ValidationError.EmailEmpty -> Res.string.error_email_empty
        ValidationError.EmailInvalid -> Res.string.error_email_invalid
        ValidationError.ConfirmPasswordEmpty -> Res.string.error_confirm_empty
        ValidationError.PasswordsDoNotMatch -> Res.string.error_passwords_mismatch
    }
)
