package com.example.belindas_closet.model

class ProductCategory (
    var name: String,
    val image: String,
    private var products: List<Product>
){
    fun getProducts(): List<Product>{
        return products
    }
}