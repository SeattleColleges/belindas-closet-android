package com.example.belindas_closet.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductRequest(
    val productType: String,
    val productGender: String,
    val productSizeShoe: String,
    val productSizes: String,
    val productSizePantsWaist: String,
    val productSizePantsInseam: String,
    val productDescription: String,
    val productImage: String,
)

