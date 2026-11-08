package ua.morozova.popcorntime.domain.auth

import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
    val isLoggedIn: StateFlow<Boolean>
    suspend fun login(username: String, password: String): Result<Unit>
    fun logout()
}