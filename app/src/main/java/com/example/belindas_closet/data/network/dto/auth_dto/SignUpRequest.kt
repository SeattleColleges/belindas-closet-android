package com.example.belindas_closet.data.network.dto.auth_dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Role(val role: String) {
    @SerialName("admin")
    ADMIN("admin"),

    @SerialName("user")
    USER("user")
}

@Serializable
data class SignUpRequest(
    @SerialName("firstName")
    val firstName: String,

    @SerialName("lastName")
    val lastName: String,

    @SerialName("email")
    val email: String,

    @SerialName("password")
    val password: String,

    @SerialName("role")
    val role: Role
)