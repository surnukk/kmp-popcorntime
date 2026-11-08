package ua.morozova.popcorntime.data.network

import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ua.morozova.popcorntime.data.auth.model.request.RefreshRequest
import ua.morozova.popcorntime.data.security.SecureStorage
import ua.morozova.popcorntime.data.auth.service.AuthApiService

class AuthorizedApiClient(
    private val client: HttpClient,
    private val storage: SecureStorage,
    private val authApi: AuthApiService
) {
    // Щоб кілька паралельних запитів не оновлювали токен одночасно
    private val refreshMutex = Mutex()

    suspend fun get(url: String): HttpResponse = execute { token ->
        client.get(url) { bearerAuth(token) }
    }

    private suspend fun execute(call: suspend (accessToken: String) -> HttpResponse): HttpResponse {
        val token = storage.getAccessToken()
            ?: throw ApiException(HttpStatusCode.Unauthorized.value)

        val response = call(token)
        if (!response.isAuthError()) return response

        val freshToken = refreshAccessToken(failedToken = token) ?: return response
        return call(freshToken)
    }

    private suspend fun refreshAccessToken(failedToken: String): String? = refreshMutex.withLock {
        val current = storage.getAccessToken()
        if (current != null && current != failedToken) return current

        val refreshToken = storage.getRefreshToken() ?: return null
        try {
            val tokens = authApi.refresh(RefreshRequest(refreshToken))
            storage.saveTokens(tokens.accessToken, tokens.refreshToken)
            println("Auth: tokens refreshed")
            tokens.accessToken
        } catch (e: CancellationException) {
            throw e
        } catch (e: ApiException) {
            // Сервер відхилив refresh-токен: сесія закінчилась
            println("Auth: refresh rejected (${e.statusCode}), clearing session")
            storage.clear()
            null
        }
        // Інші помилки (наприклад, нема мережі) летять далі, але сесію не скидають
    }

    private fun HttpResponse.isAuthError() =
        status == HttpStatusCode.Unauthorized || status == HttpStatusCode.Forbidden
}