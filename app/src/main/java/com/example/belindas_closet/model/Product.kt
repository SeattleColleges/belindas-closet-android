package com.example.belindas_closet.model

import com.example.belindas_closet.R

enum class ProductType(val type: String, val image: Int = R.drawable.placeholder) {
    SHOES("Shoes"), SHIRTS("Shirts"), PANTS("Pants"),
    SKIRT("Skirt"), JACKET_BLAZER("Jacket/Blazer"), DRESS("Dress"),
    CASUAL_WEAR("Casual Wear"), SUITS("Suits"), ACCESSORIES("Accessories")
}

enum class ProductGender {
    MALE, FEMALE, NON_BINARY
}

// Shoes sizes
enum class ProductSizeShoes(val size: Int?) {
    SELECT_SIZE(null), XS(5), S(6), M(7), L(8), XL(9), XXL(10), XXXL(11), XXXXL(12)
}

// General product sizes
enum class ProductSizes {
    SELECT_SIZE, XXS, XS, S, M, L, XL, XXL, XXXL, XXXXL
}

// productSizePantsWaist is nullable because not all products have a waist size
enum class ProductSizePantsWaist(val size: Int?) {
    SELECT_SIZE(null), XS(28), S(30), M(32), L(34), XL(36), XXL(38), XXXL(40), XXXXL(42)
}

// productSizePantsInseam is nullable because not all products have a inseam size
enum class ProductSizePantsInseam(val size: Int?) {
    SELECT_SIZE(null), XS(28), S(30), M(32), L(34), XL(36), XXL(38), XXXL(40), XXXXL(42)
}

data class Product(
    val productType: ProductType,
    val productGender: ProductGender,
    val productSizeShoe: ProductSizeShoes,
    val productSizes: ProductSizes,
    val productSizePantsWaist: ProductSizePantsWaist,
    val productSizePantsInseam: ProductSizePantsInseam,
    val productDescription: String,
    val productImage: String = R.drawable.placeholder.toString(),
    val id: String = "0"
)