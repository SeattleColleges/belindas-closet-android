package com.example.belindas_closet.data

import com.example.belindas_closet.R
import com.example.belindas_closet.model.Product
import com.example.belindas_closet.model.ProductGender
import com.example.belindas_closet.model.ProductSizes
import com.example.belindas_closet.model.ProductSizePantsInseam
import com.example.belindas_closet.model.ProductSizePantsWaist
import com.example.belindas_closet.model.ProductSizeShoes
import com.example.belindas_closet.model.ProductType

class Datasource {
    private val productList = mutableListOf<Product>()

    init {
        productList.add(
            Product(
                ProductType.DRESS,
                ProductGender.FEMALE,
                ProductSizeShoes.SELECT_SIZE,
                ProductSizes.XS,
                ProductSizePantsWaist.XS,
                ProductSizePantsInseam.XS,
                "This is a beautiful dress",
                R.drawable.product1.toString()
            )
        )
        productList.add(
            Product(
                ProductType.SHOES,
                ProductGender.MALE,
                ProductSizeShoes.M,
                ProductSizes.M,
                ProductSizePantsWaist.SELECT_SIZE,
                ProductSizePantsInseam.SELECT_SIZE,
                "This is a pair of shoes",
                R.drawable.product4.toString()
            )
        )
        productList.add(
            Product(
                ProductType.PANT,
                ProductGender.MALE,
                ProductSizeShoes.SELECT_SIZE,
                ProductSizes.M,
                ProductSizePantsWaist.M,
                ProductSizePantsInseam.M,
                "This is a pair of pants",
                R.drawable.product3.toString()
            )
        )
        productList.add(
            Product(
                ProductType.SHORT_SLEEVE_SHIRT,
                ProductGender.MALE,
                ProductSizeShoes.SELECT_SIZE,
                ProductSizes.M,
                ProductSizePantsWaist.M,
                ProductSizePantsInseam.M,
                "This is a short sleeve shirt",
                R.drawable.product2.toString()
            )
        )
    }


    fun loadProducts(): List<Product> {
        return productList
    }
}