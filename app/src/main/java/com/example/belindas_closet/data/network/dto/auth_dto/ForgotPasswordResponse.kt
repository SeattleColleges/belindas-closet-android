package com.example.belindas_closet.data.network.dto.auth_dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForgotPasswordResponse(
    @SerialName("newToken")
    val newToken: String
)
