package com.example.belindas_closet.data.network.dto.product_dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeleteResponse(
    @SerialName("isHidden")
    val isHidden: Boolean = false
)