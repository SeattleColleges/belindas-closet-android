package com.example.belindas_closet.data.network.product

import com.example.belindas_closet.data.network.HttpRoutes
import com.example.belindas_closet.data.network.dto.product_dto.ProductRequest
import com.example.belindas_closet.data.network.dto.product_dto.ProductResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.util.InternalAPI
import kotlinx.serialization.json.Json
import okhttp3.ResponseBody.Companion.toResponseBody

class ProductServiceImpl (
    private val client: HttpClient,
    private val getToken: suspend () -> String
) : ProductService {
    override suspend fun getProduct(productRequest: ProductRequest): ProductRequest? {
        TODO("Not yet implemented")
    }

    @OptIn(InternalAPI::class)
    override suspend fun getProducts(productRequest: ProductRequest): List<ProductRequest>? {
        return try {
            val token = getToken()
            val response = client.get {
                url (HttpRoutes.PRODUCTS)
                header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                header(HttpHeaders.Authorization, "Bearer $token")
                body = Json.encodeToString(ProductRequest.serializer(), productRequest)
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

    @OptIn(InternalAPI::class)
    override suspend fun addProduct(productRequest: ProductRequest): ProductResponse? {
        return try {
            val token = getToken()
            val response = client.post {
                url (HttpRoutes.ADD_PRODUCT)
                header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                header(HttpHeaders.Authorization, "Bearer $token")
                body = Json.encodeToString(ProductRequest.serializer(), productRequest)
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