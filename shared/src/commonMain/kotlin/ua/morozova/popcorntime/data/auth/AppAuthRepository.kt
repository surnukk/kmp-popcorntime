package ua.morozova.popcorntime.data.auth

import kotlinx.coroutines.flow.StateFlow
import ua.morozova.popcorntime.data.auth.model.request.LoginRequest
import ua.morozova.popcorntime.data.network.safeApiCall
import ua.morozova.popcorntime.data.security.SecureStorage
import ua.morozova.popcorntime.data.auth.service.AuthApiService
import ua.morozova.popcorntime.domain.auth.AuthRepository

class AppAuthRepository(
    private val api: AuthApiService,
    private val storage: SecureStorage
) : AuthRepository {

    override val isLoggedIn: StateFlow<Boolean> = storage.hasSession

    override suspend fun login(username: String, password: String): Result<Unit> = safeApiCall {
        val response = api.login(LoginRequest(username, password))
        storage.saveTokens(response.accessToken, response.refreshToken)
    }

    override fun logout() = storage.clear()
}