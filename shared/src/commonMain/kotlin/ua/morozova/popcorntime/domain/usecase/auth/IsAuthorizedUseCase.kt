package ua.morozova.popcorntime.domain.usecase.auth

import kotlinx.coroutines.flow.StateFlow
import ua.morozova.popcorntime.domain.auth.AuthRepository

class IsAuthorizedUseCase(private val repository: AuthRepository) {
    operator fun invoke(): StateFlow<Boolean> = repository.isLoggedIn
}