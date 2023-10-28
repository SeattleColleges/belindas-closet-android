package com.example.belindas_closet.data.network

object HttpRoutes {
    private const val BASE_URL = "http://10.0.2.2:3000/api"
    const val PRODUCTS = "$BASE_URL/products"
    const val PRODUCT = "$BASE_URL/products/{id}"
}