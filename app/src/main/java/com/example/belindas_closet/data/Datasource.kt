package com.example.belindas_closet.data

import com.example.belindas_closet.R
import com.example.belindas_closet.model.Product
import com.example.belindas_closet.model.ProductCategory
import com.example.belindas_closet.model.Sizes

class Datasource {
    fun loadProducts(): Map<String, out ProductCategory> {
        // TODO: Replace with API call to get products from backend
        return mapOf(
            "Dresses" to ProductCategory(
                "Dresses (Category)",
                R.drawable.product1.toString(),
                listOf(
                    Product("Dresses_Dress", "Dress", Sizes.S, "dd1", R.drawable.product1.toString()),
                    Product("Dresses_Shirt", "Shirt", Sizes.M, "ds1", R.drawable.product2.toString()),
                    Product("Dresses_Pants", "Pants", Sizes.L, "dp1",R.drawable.product3.toString()),
                    Product("Dresses_Shoes", "Shoes", Sizes.XL, "ds2", R.drawable.product4.toString()),
                )
            ),
            "Shirts" to ProductCategory(
                "Shirts (Category)",
                R.drawable.product2.toString(),
                listOf(
                    Product("Shirts_Dress", "Dress",  Sizes.S, "sd1",R.drawable.product1.toString()),
                    Product("Shirts_Shirt", "Shirt", Sizes.M, "ss1", R.drawable.product2.toString()),
                    Product("Shirts_Pants", "Pants", Sizes.L, "sp1", R.drawable.product3.toString()),
                    Product("Shirts_Shoes", "Shoes", Sizes.XL, "ss2", R.drawable.product4.toString()),
                )
            ),
            "Pants" to ProductCategory(
                "Pants (Category)",
                R.drawable.product3.toString(),
                listOf(
                    Product("Pants_Dress", "Dress", Sizes.S, "pd1", R.drawable.product1.toString()),
                    Product("Pants_Shirt", "Shirt", Sizes.M, "ps1", R.drawable.product2.toString()),
                    Product("Pants_Pants", "Pants", Sizes.L, "pp1", R.drawable.product3.toString()),
                    Product("Pants_Shoes", "Shoes", Sizes.XL, "ps2", R.drawable.product4.toString()),
                )
            ),
            "Shoes" to ProductCategory(
                "Shoes (Category)",
                R.drawable.product4.toString(),
                listOf(
                    Product("Shoes_Dress", "Dress", Sizes.S, "sd2", R.drawable.product1.toString()),
                    Product("Shoes_Shirt", "Shirt", Sizes.M, "ss3", R.drawable.product2.toString()),
                    Product("Shoes_Pants", "Pants", Sizes.L, "sp2", R.drawable.product3.toString()),
                    Product("Shoes_Shoes", "Shoes", Sizes.XL, "ss4", R.drawable.product4.toString()),
                )
            ),
        )
    }
}