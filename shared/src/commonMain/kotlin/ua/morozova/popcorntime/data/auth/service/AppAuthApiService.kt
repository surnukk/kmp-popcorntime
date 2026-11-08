package ua.morozova.popcorntime.data.auth.service

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import ua.morozova.popcorntime.data.auth.model.request.LoginRequest
import ua.morozova.popcorntime.data.auth.model.request.RefreshRequest
import ua.morozova.popcorntime.data.auth.model.response.LoginResponse
import ua.morozova.popcorntime.data.auth.model.response.RefreshResponse
import ua.morozova.popcorntime.data.network.ApiConfig
import ua.morozova.popcorntime.data.network.bodyOrThrow

class AppAuthApiService(private val client: HttpClient) : AuthApiService {

    override suspend fun login(request: LoginRequest): LoginResponse =
        client.post("${ApiConfig.BASE_URL}/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.bodyOrThrow()

    override suspend fun refresh(request: RefreshRequest): RefreshResponse =
        client.post("${ApiConfig.BASE_URL}/auth/refresh") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.bodyOrThrow()
}