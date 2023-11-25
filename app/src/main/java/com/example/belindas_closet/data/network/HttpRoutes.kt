package com.example.belindas_closet.data.network

object HttpRoutes {
    private const val BASE_URL = "http://10.0.2.2:3000/api"
    const val PRODUCTS = "$BASE_URL/products"
    const val PRODUCT = "$BASE_URL/products/{id}"
    const val ARCHIVE = "$BASE_URL/products/archive"
    const val DELETE = "$BASE_URL/products/remove"
    const val LOGIN = "$BASE_URL/auth/login"
    const val FORGOT_PASSWORD = "$BASE_URL/auth/forgot-password"
    const val SIGNUP = "$BASE_URL/auth/signup"
}