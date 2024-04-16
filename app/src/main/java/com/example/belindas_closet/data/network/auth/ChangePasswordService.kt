package com.example.belindas_closet.data.network.auth

import com.example.belindas_closet.MainActivity
import com.example.belindas_closet.data.network.dto.auth_dto.ChangePasswordRequest
import com.example.belindas_closet.data.network.dto.auth_dto.ChangePasswordResponse
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import io.ktor.client.plugins.logging.Logger

interface ChangePasswordService {
    suspend fun changePassword(changePasswordRequest: ChangePasswordRequest): ChangePasswordResponse?

    companion object {
        fun create(): ChangePasswordService {
            return ChangePasswordServiceImpl(
                client = HttpClient(Android) {
                    install(ContentNegotiation) {
                        json(Json {
                            prettyPrint = true
                            isLenient = true
                            ignoreUnknownKeys = true
                        })
                    }
                    install(Logging) {
                        level = LogLevel.ALL
                        logger = Logger.DEFAULT
                    }
                },
                getToken = suspend {
                    MainActivity.getPref().getString("token", "") ?: ""
                },
            )
        }
    }
}

