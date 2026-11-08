package ua.morozova.popcorntime.data.security

import kotlinx.coroutines.flow.StateFlow

interface SecureStorage {
    val hasSession: StateFlow<Boolean>
    fun getAccessToken(): String?
    fun getRefreshToken(): String?
    fun saveTokens(accessToken: String, refreshToken: String)
    fun clear()
}