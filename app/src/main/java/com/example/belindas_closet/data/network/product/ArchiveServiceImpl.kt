package com.example.belindas_closet.data.network.product

import com.example.belindas_closet.data.network.HttpRoutes
import com.example.belindas_closet.data.network.dto.product_dto.ArchiveRequest
import com.example.belindas_closet.data.network.dto.product_dto.ArchiveResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.header
import io.ktor.client.request.put
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.util.InternalAPI
import kotlinx.serialization.json.Json

class ArchiveServiceImpl (
    private val client: HttpClient,
    private val getToken: suspend () -> String
) : ArchiveService {
    @OptIn(InternalAPI::class)
    override suspend fun archive(archiveRequest: ArchiveRequest): ArchiveResponse? {
        return try {
            val token = getToken()
            val response = client.put {
                url("${HttpRoutes.ARCHIVE}/${archiveRequest.id}")
                header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                header(HttpHeaders.Authorization, "Bearer $token")
                body = Json.encodeToString(ArchiveRequest.serializer(), archiveRequest)
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