package com.example.belindas_closet.data.network.auth

import com.example.belindas_closet.data.network.dto.auth_dto.ForgotPasswordRequest
import com.example.belindas_closet.data.network.dto.auth_dto.ForgotPasswordResponse
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

interface ForgotPasswordService {
    suspend fun forgotPassword(forgotPasswordService: ForgotPasswordRequest) : ForgotPasswordResponse?

    companion object {
        fun create() : ForgotPasswordService {
            return ForgotPasswordServiceImpl(
                client = HttpClient(Android) {
                    install(ContentNegotiation) {
                        json(Json {
                            prettyPrint = true
                            isLenient = true
                            ignoreUnknownKeys = true
                        })
                    }
                    install (Logging) {
                        level = LogLevel.ALL
                        logger = Logger.DEFAULT
                    }
                }
            )
        }
    }
}