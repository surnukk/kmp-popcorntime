package ua.morozova.popcorntime.domain.usecase.auth

import ua.morozova.popcorntime.domain.auth.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(username: String, password: String): Result<Unit> =
        repository.login(username.trim(), password)
}