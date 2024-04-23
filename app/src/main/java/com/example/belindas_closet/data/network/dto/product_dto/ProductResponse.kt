package com.example.belindas_closet.data.network.dto.product_dto

import com.example.belindas_closet.model.ProductGender
import com.example.belindas_closet.model.ProductSizePantsInseam
import com.example.belindas_closet.model.ProductSizePantsWaist
import com.example.belindas_closet.model.ProductSizeShoes
import com.example.belindas_closet.model.ProductSizes
import com.example.belindas_closet.model.ProductType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(
    @SerialName("productType")
    val productType: ProductType,

    @SerialName("productGender")
    val productGender: ProductGender,

    @SerialName("productSizeShoe")
    val productSizeShoe: ProductSizeShoes,

    @SerialName("productSizes")
    val productSizes: ProductSizes,

    @SerialName("productSizePantsWaist")
    val productSizePantsWaist: ProductSizePantsWaist,

    @SerialName("productSizePantsInseam")
    val productSizePantsInseam: ProductSizePantsInseam,

    @SerialName("productDescription")
    val productDescription: String,

    @SerialName("productImage")
    val productImage: String,

    @SerialName("isHidden")
    val isHidden: Boolean,

    @SerialName("isSold")
    val isSold: Boolean,

    @SerialName("id")
    val id: String
)