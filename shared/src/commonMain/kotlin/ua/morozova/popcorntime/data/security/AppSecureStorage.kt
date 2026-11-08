package ua.morozova.popcorntime.data.security

import eu.anifantakis.lib.ksafe.KSafe   // якщо червоне, Alt+Enter підкаже правильний пакет
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppSecureStorage(private val ksafe: KSafe) : SecureStorage {

    private val _hasSession = MutableStateFlow(getRefreshToken() != null)
    override val hasSession: StateFlow<Boolean> = _hasSession.asStateFlow()

    override fun getAccessToken(): String? = read(KEY_ACCESS)

    override fun getRefreshToken(): String? = read(KEY_REFRESH)

    override fun saveTokens(accessToken: String, refreshToken: String) {
        ksafe.putDirect(KEY_ACCESS, accessToken)
        ksafe.putDirect(KEY_REFRESH, refreshToken)
        _hasSession.value = true
    }

    override fun clear() {
        ksafe.putDirect(KEY_ACCESS, "")
        ksafe.putDirect(KEY_REFRESH, "")
        _hasSession.value = false
    }

    // Тип <String> вказуємо явно, інакше Kotlin виводить Any
    private fun read(key: String): String? {
        val value: String = ksafe.getDirect<String>(key, "")
        return value.ifBlank { null }
    }

    private companion object {
        const val KEY_ACCESS = "access_token"
        const val KEY_REFRESH = "refresh_token"
    }
}