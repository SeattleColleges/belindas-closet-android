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
        productList.add(
            Product(
                ProductType.LONG_SLEEVE_SHIRT,
                ProductGender.FEMALE,
                ProductSizeShoes.SELECT_SIZE,
                ProductSizes.M,
                ProductSizePantsWaist.M,
                ProductSizePantsInseam.M,
                "This is a long sleeve shirt",
                R.drawable.product5.toString()
            )
        )
        productList.add(
            Product(
                ProductType.SKIRT,
                ProductGender.FEMALE,
                ProductSizeShoes.SELECT_SIZE,
                ProductSizes.M,
                ProductSizePantsWaist.M,
                ProductSizePantsInseam.M,
                "This is a skirt",
                R.drawable.product6.toString()
            )
        )
        productList.add(
            Product(
                ProductType.TIE,
                ProductGender.NON_BINARY,
                ProductSizeShoes.SELECT_SIZE,
                ProductSizes.M,
                ProductSizePantsWaist.M,
                ProductSizePantsInseam.M,
                "This is a tie",
                R.drawable.product7.toString()
            )
        )
        productList.add(
            Product(
                ProductType.BELT,
                ProductGender.NON_BINARY,
                ProductSizeShoes.SELECT_SIZE,
                ProductSizes.M,
                ProductSizePantsWaist.M,
                ProductSizePantsInseam.M,
                "This is a belt",
                R.drawable.product8.toString()
            )
        )
        productList.add(
            Product(
                ProductType.JACKETS,
                ProductGender.MALE,
                ProductSizeShoes.SELECT_SIZE,
                ProductSizes.M,
                ProductSizePantsWaist.M,
                ProductSizePantsInseam.M,
                "This is a jacket",
                R.drawable.product9.toString()
            )
        )
        productList.add(
            Product(
                ProductType.HAT,
                ProductGender.NON_BINARY,
                ProductSizeShoes.SELECT_SIZE,
                ProductSizes.M,
                ProductSizePantsWaist.M,
                ProductSizePantsInseam.M,
                "This is a hat",
                R.drawable.product10.toString()
            )
        )
        productList.add(
            Product(
                ProductType.SOCKS,
                ProductGender.NON_BINARY,
                ProductSizeShoes.SELECT_SIZE,
                ProductSizes.M,
                ProductSizePantsWaist.M,
                ProductSizePantsInseam.M,
                "This is a pair of socks",
                R.drawable.product11.toString()
            )
        )
        productList.add(
            Product(
                ProductType.HANDBAG,
                ProductGender.FEMALE,
                ProductSizeShoes.SELECT_SIZE,
                ProductSizes.M,
                ProductSizePantsWaist.M,
                ProductSizePantsInseam.M,
                "This is a handbag",
                R.drawable.product12.toString()
            )
        )
        productList.add(
            Product(
                ProductType.SCARF,
                ProductGender.NON_BINARY,
                ProductSizeShoes.SELECT_SIZE,
                ProductSizes.M,
                ProductSizePantsWaist.M,
                ProductSizePantsInseam.M,
                "This is a scarf",
                R.drawable.product13.toString()
            )
        )
        productList.add(
            Product(
                ProductType.GOWN,
                ProductGender.NON_BINARY,
                ProductSizeShoes.SELECT_SIZE,
                ProductSizes.M,
                ProductSizePantsWaist.M,
                ProductSizePantsInseam.M,
                "This is a gown",
                R.drawable.product14.toString()
            )
        )
        productList.add(
            Product(
                ProductType.HOODIE,
                ProductGender.MALE,
                ProductSizeShoes.SELECT_SIZE,
                ProductSizes.M,
                ProductSizePantsWaist.M,
                ProductSizePantsInseam.M,
                "This is a hoodie",
                R.drawable.product15.toString()
            )
        )
        productList.add(
            Product(
                ProductType.SWEATER,
                ProductGender.FEMALE,
                ProductSizeShoes.SELECT_SIZE,
                ProductSizes.M,
                ProductSizePantsWaist.M,
                ProductSizePantsInseam.M,
                "This is a sweater",
                R.drawable.product16.toString()
            )
        )
        productList.add(
            Product(
                ProductType.VEST,
                ProductGender.MALE,
                ProductSizeShoes.SELECT_SIZE,
                ProductSizes.M,
                ProductSizePantsWaist.M,
                ProductSizePantsInseam.M,
                "This is a vest",
                R.drawable.product17.toString()
            )
        )
        productList.add(
            Product(
                ProductType.OTHER,
                ProductGender.NON_BINARY,
                ProductSizeShoes.SELECT_SIZE,
                ProductSizes.M,
                ProductSizePantsWaist.M,
                ProductSizePantsInseam.M,
                "This is a pair of gloves",
                R.drawable.product18.toString()
            )
        )
    }


    fun loadProducts(): List<Product> {
        return productList
    }
}