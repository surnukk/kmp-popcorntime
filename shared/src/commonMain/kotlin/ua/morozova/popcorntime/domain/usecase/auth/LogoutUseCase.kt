package ua.morozova.popcorntime.domain.usecase.auth

import ua.morozova.popcorntime.domain.auth.AuthRepository

class LogoutUseCase(private val repository: AuthRepository) {
    operator fun invoke() = repository.logout()
}