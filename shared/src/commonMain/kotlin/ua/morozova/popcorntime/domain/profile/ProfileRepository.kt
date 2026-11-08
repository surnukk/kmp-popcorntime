package ua.morozova.popcorntime.domain.profile

import ua.morozova.popcorntime.domain.profile.User

interface ProfileRepository {
    suspend fun getCurrentUser(): Result<User>
}