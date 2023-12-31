package com.example.belindas_closet.data.network.product

import com.example.belindas_closet.MainActivity
import com.example.belindas_closet.data.network.dto.product_dto.ProductRequest
import com.example.belindas_closet.data.network.dto.product_dto.ProductResponse
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

interface ProductService {
    suspend fun getProduct(productRequest: ProductRequest): ProductResponse?

    suspend fun getProducts(): List<ProductResponse>?

    suspend fun addProduct(productRequest: ProductRequest): ProductResponse?

    companion object {
        fun create(): ProductService {
            return ProductServiceImpl(
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
                },
                getToken = suspend {
                    MainActivity.getPref().getString("token", "") ?: ""
                },
            )
        }
    }
}