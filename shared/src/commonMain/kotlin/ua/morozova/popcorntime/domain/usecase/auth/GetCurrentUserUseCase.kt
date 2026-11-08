package ua.morozova.popcorntime.domain.usecase.auth

import ua.morozova.popcorntime.domain.profile.ProfileRepository
import ua.morozova.popcorntime.domain.profile.User

class GetCurrentUserUseCase(private val repository: ProfileRepository) {
    suspend operator fun invoke(): Result<User> = repository.getCurrentUser()
}