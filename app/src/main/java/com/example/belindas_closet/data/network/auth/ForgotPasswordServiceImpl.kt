package com.example.belindas_closet.data.network.auth


import com.example.belindas_closet.data.network.HttpRoutes
import com.example.belindas_closet.data.network.dto.auth_dto.ForgotPasswordRequest
import com.example.belindas_closet.data.network.dto.auth_dto.ForgotPasswordResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.util.InternalAPI
import kotlinx.serialization.json.Json

class ForgotPasswordServiceImpl(
    private val client: HttpClient
) :
    ForgotPasswordService {
    @OptIn(InternalAPI::class)
    override suspend fun forgotPassword(forgotPasswordRequest: ForgotPasswordRequest): ForgotPasswordResponse? {
        return try {
            val response = client.post {
                url(HttpRoutes.FORGOT_PASSWORD)
                header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                body = Json.encodeToString(ForgotPasswordRequest.serializer(), forgotPasswordRequest)
            }
            response.body()
        } catch (e: Exception) {
            println("Error: ${e.message}")
            null
        }
    }

}
