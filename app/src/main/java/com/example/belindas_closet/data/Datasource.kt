package com.example.belindas_closet.data

import com.example.belindas_closet.R
import com.example.belindas_closet.model.Product
import com.example.belindas_closet.model.ProductType
import com.example.belindas_closet.model.Sizes

class Datasource {
    fun loadProducts(): List<Product> {
        // TODO: Replace with API call to get products from backend
        return listOf(
            Product(ProductType.DRESSES,"Dresses", Sizes.S, R.drawable.product1.toString()),
            Product(ProductType.SHIRTS, "Shirts", Sizes.M, R.drawable.product2.toString()),
            Product(ProductType.PANTS, "Pants", Sizes.L, R.drawable.product3.toString()),
            Product(ProductType.SHOES, "Shoes", Sizes.XL, R.drawable.product4.toString()),
        )
    }
}