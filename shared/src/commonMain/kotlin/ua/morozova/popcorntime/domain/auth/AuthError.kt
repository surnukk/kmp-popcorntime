package ua.morozova.popcorntime.domain.auth

enum class AuthError { InvalidCredentials, Network, Server, Unknown }

class AuthException(val error: AuthError) : Exception()