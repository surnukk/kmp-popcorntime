package ua.morozova.popcorntime.data.profile

import ua.morozova.popcorntime.data.network.safeApiCall
import ua.morozova.popcorntime.data.profile.service.ProfileApiService
import ua.morozova.popcorntime.domain.profile.User
import ua.morozova.popcorntime.domain.profile.ProfileRepository

class AppProfileRepository(private val api: ProfileApiService) : ProfileRepository {
    override suspend fun getCurrentUser(): Result<User> = safeApiCall { api.getMe().toDomain() }
}