package ua.morozova.popcorntime.data.profile.model

import kotlinx.serialization.Serializable
import ua.morozova.popcorntime.domain.profile.User

@Serializable
data class UserResponse(
    val id: Int,
    val username: String,
    val firstName: String,
    val lastName: String,
    val email: String
) {
    fun toDomain() = User(id, username, firstName, lastName, email)
}