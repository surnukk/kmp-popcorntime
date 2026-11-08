package ua.morozova.popcorntime.data.network

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException
import ua.morozova.popcorntime.domain.auth.AuthError
import ua.morozova.popcorntime.domain.auth.AuthException

class ApiException(val statusCode: Int) : Exception("HTTP $statusCode")

suspend inline fun <reified T> HttpResponse.bodyOrThrow(): T {
    if (!status.isSuccess()) throw ApiException(statusCode = status.value)
    return body()
}

inline fun <T> safeApiCall(block: () -> T): Result<T> = try {
    Result.success(block())
} catch (e: CancellationException) {
    throw e
} catch (e: ApiException) {
    Result.failure(
        AuthException(
            when (e.statusCode) {
                400, 401, 403 -> AuthError.InvalidCredentials
                in 500..599 -> AuthError.Server
                else -> AuthError.Unknown
            }
        )
    )
} catch (e: SerializationException) {
    Result.failure(AuthException(AuthError.Unknown))
} catch (e: Exception) {
    Result.failure(AuthException(AuthError.Network))
}