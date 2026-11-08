package ua.morozova.popcorntime.domain.auth

import org.jetbrains.compose.resources.StringResource
import popcorntime.shared.generated.resources.*
import ua.morozova.popcorntime.domain.auth.AuthError

fun AuthError.toStringResource(): StringResource = when (this) {
    AuthError.InvalidCredentials -> Res.string.error_invalid_credentials
    AuthError.Network -> Res.string.error_network
    AuthError.Server -> Res.string.error_server
    AuthError.Unknown -> Res.string.error_unknown
}