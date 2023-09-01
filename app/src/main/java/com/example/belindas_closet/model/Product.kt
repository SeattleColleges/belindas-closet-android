package com.example.belindas_closet.model

/* TODO: maybe change the size variable to a enum. Also add more enum sizes */
enum class Sizes {
    SELECT_SIZE, XXS, XS, S, M, L, XL, XXL, XXXL, XXXXL
}

data class Product(
    var name: String,
    var description: String,
    var size: Sizes,
    val image: String,
    /* TODO: what other product properties should be added? */
)