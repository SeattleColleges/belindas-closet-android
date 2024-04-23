package com.example.belindas_closet.data.network.dto.auth_dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChangePasswordRequest(
    @SerialName("currentPassword")
    val currentPassword: String,

    @SerialName("newPassword")
    val newPassword: String,

    @SerialName("confirmPassword")
    val confirmPassword: String
)
