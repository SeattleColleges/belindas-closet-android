package com.example.belindas_closet.data.network.auth

import com.example.belindas_closet.data.network.HttpRoutes
import com.example.belindas_closet.data.network.dto.auth_dto.SignUpRequest
import com.example.belindas_closet.data.network.dto.auth_dto.SignUpResponse
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

class SignUpServiceImpl (
    private val client: HttpClient
) : SignUpService {
    @OptIn(InternalAPI::class)
    override suspend fun signup(signUpRequest: SignUpRequest): SignUpResponse? {
        return try {
            val response = client.post {
                url(HttpRoutes.SIGNUP)
                header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                body = Json.encodeToString(SignUpRequest.serializer(), signUpRequest)
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