package com.example.belindas_closet.data

/* TODO: maybe change the size variable to a enum. Also add more enum sizes */
enum class Sizes {
    SMALL, MEDIUM, LARGE
}

data class Product(
    val name: String,
    val description: String,
    val size: String
    /* TODO: what other product properties should be added? */
)
