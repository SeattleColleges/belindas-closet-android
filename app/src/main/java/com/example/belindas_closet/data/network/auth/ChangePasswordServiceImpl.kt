package com.example.belindas_closet.data.network.auth

import com.example.belindas_closet.data.network.HttpRoutes
import com.example.belindas_closet.data.network.dto.auth_dto.ChangePasswordRequest
import com.example.belindas_closet.data.network.dto.auth_dto.ChangePasswordResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.util.InternalAPI
import kotlinx.serialization.json.Json


class ChangePasswordServiceImpl (
    private val client: HttpClient,
    private val getToken: suspend () -> String
) : ChangePasswordService {
    @OptIn(InternalAPI::class)
    override suspend fun changePassword(changePasswordRequest: ChangePasswordRequest): ChangePasswordResponse? {
        return try {
            val token = getToken()
            val response = client.post {
                url(HttpRoutes.CHANGE_PASSWORD)
                header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                header(HttpHeaders.Authorization, "Bearer $token")
                body = Json.encodeToString(ChangePasswordRequest.serializer(), changePasswordRequest)
            }
            response.body()
        } catch (e: RedirectResponseException) {
            println("Error: ${e.response.status.description}")
            null
        } catch (e: ClientRequestException) {
            println("Error: ${e.response.status.description}")
            null
        } catch (e: ServerResponseException) {
            println("Error: ${e.response.status.description}")
            null
        } catch (e: Exception) {
            println("Error: ${e.message}")
            null
        }
    }
}