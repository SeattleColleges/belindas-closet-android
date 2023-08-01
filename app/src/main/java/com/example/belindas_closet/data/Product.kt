package com.example.belindas_closet.data

/* TODO: maybe change the size variable to a enum. Also add more enum sizes */
enum class Sizes {
    SELECT_SIZE, XXS, XS, S, M, L, XL, XXL, XXXL, XXXXL
}

data class Product(
    val name: String,
    val description: String,
    val size: Sizes
    /* TODO: what other product properties should be added? */
)
