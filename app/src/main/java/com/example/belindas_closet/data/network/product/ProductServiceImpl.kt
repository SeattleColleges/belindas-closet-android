package com.example.belindas_closet.data.network.product


import com.example.belindas_closet.data.network.HttpRoutes
import com.example.belindas_closet.data.network.dto.product_dto.ProductResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get

class ProductServiceImpl (
    private val client: HttpClient
) : ProductService {

    override suspend fun getProducts(): List<ProductResponse> {
        return try {
            val response : ProductResponse = client.get(HttpRoutes.PRODUCTS).body()
            listOf(response)
        } catch (e: RedirectResponseException) {
            println("Error: ${e.response.status.description}")
            emptyList()
        } catch (e: ClientRequestException) {
            println("Error: ${e.response.status.description}")
            emptyList()
        } catch (e: ServerResponseException) {
            println("Error: ${e.response.status.description}")
            emptyList()
        } catch (e: Exception) {
            println("Error: ${e.message}")
            emptyList()
        }
    }

    override suspend fun getProductById(id: Int): ProductResponse {
        return client.get (HttpRoutes.PRODUCTS + "/$id").body()
    }
}