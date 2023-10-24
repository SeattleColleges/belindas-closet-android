package com.example.belindas_closet.model

/* TODO: maybe change the size variable to a enum. Also add more enum sizes */
enum class Sizes {
    SELECT_SIZE, XXS, XS, S, M, L, XL, XXL, XXXL, XXXXL
}

enum class ProductType {
    SHIRTS, PANTS, SHOES, DRESSES
}

data class Product(
    var productType: ProductType,
    var description: String,
    var size: Sizes,
    val image: String,
    /* TODO: what other product properties should be added? */
)