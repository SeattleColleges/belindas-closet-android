package com.example.belindas_closet.data.network.dto.product_dto

import com.example.belindas_closet.model.ProductGender
import com.example.belindas_closet.model.ProductSizes
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductRequest(
    @SerialName("productType")
    val productType: String,

    @SerialName("productGender")
    val productGender: ProductGender,

    @SerialName("productSizeShoe")
    val productSizeShoe: Int?,

    @SerialName("productSizes")
    val productSizes: ProductSizes,

    @SerialName("productSizePantsWaist")
    val productSizePantsWaist: Int?,

    @SerialName("productSizePantsInseam")
    val productSizePantsInseam: Int?,

    @SerialName("productDescription")
    val productDescription: String,

    @SerialName("productImage")
    val productImage: String
)