package com.example.belindas_closet.data.network.auth

import com.example.belindas_closet.data.network.HttpRoutes
import com.example.belindas_closet.data.network.dto.auth_dto.LoginRequest
import com.example.belindas_closet.data.network.dto.auth_dto.LoginResponse
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

class LoginServiceImpl (
    private val client: HttpClient
) : LoginService {
    @OptIn(InternalAPI::class)
    override suspend fun login(loginRequest: LoginRequest): LoginResponse? {
        return try {
            val response = client.post {
                url(HttpRoutes.LOGIN)
                header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                body = Json.encodeToString(LoginRequest.serializer(), loginRequest)
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