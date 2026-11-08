package ua.morozova.popcorntime.data.auth.model.request

import kotlinx.serialization.Serializable
import ua.morozova.popcorntime.data.network.ApiConfig

@Serializable
data class LoginRequest(
    val username: String,
    val password: String,
    val expiresInMins: Int = ApiConfig.TOKEN_LIFETIME_MINUTES
)