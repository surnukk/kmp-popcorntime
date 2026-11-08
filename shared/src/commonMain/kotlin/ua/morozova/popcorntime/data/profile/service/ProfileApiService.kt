package ua.morozova.popcorntime.data.profile.service

import ua.morozova.popcorntime.data.network.ApiConfig
import ua.morozova.popcorntime.data.network.AuthorizedApiClient
import ua.morozova.popcorntime.data.network.bodyOrThrow
import ua.morozova.popcorntime.data.profile.model.UserResponse

class ProfileApiService(private val client: AuthorizedApiClient) {
    suspend fun getMe(): UserResponse =
        client.get("${ApiConfig.BASE_URL}/auth/me").bodyOrThrow()
}