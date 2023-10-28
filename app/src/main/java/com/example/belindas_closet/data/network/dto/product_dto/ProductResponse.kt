package com.example.belindas_closet.data.network.dto.product_dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse (
    val products: List<Products>
)

@Serializable
data class Products(
    val productType: List<String>,
    val gender: List<String>,
    val productShoeSize: List<String>,
    val productSize: List<String>,
    val productSizePantsWaist: List<String>,
    val productSizePantsInseam: List<String>,
    val productDescriptionOptional: String,
    val productImage: String,
    val createdAt: String,
    val updatedAt: String,
)

