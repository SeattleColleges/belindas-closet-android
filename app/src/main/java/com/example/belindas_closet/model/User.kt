package com.example.belindas_closet.model

import com.example.belindas_closet.data.network.dto.auth_dto.Role

data class User(
    val userFirstName: String,
    val userLastName: String,
    val userEmail: String,
    var userRole: Role,
    val userId: String = "0"
)
