package ua.morozova.popcorntime.data.auth.service

import ua.morozova.popcorntime.data.auth.model.request.LoginRequest
import ua.morozova.popcorntime.data.auth.model.request.RefreshRequest
import ua.morozova.popcorntime.data.auth.model.response.LoginResponse
import ua.morozova.popcorntime.data.auth.model.response.RefreshResponse

interface AuthApiService {
    suspend fun login(request: LoginRequest): LoginResponse
    suspend fun refresh(request: RefreshRequest): RefreshResponse
}