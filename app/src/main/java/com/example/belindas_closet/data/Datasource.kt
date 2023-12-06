package com.example.belindas_closet.data

import com.example.belindas_closet.R
import com.example.belindas_closet.model.Product
import com.example.belindas_closet.model.ProductGender
import com.example.belindas_closet.model.ProductSizePantsInseam
import com.example.belindas_closet.model.ProductSizePantsWaist
import com.example.belindas_closet.model.ProductSizeShoes
import com.example.belindas_closet.model.ProductSizes
import com.example.belindas_closet.model.ProductType
import com.example.belindas_closet.model.User
import com.example.belindas_closet.model.UserRole

class Datasource {
    private val productList = mutableListOf<Product>()
    private val userList = mutableListOf<User>()


    init {
        productList.add(
            Product(
                ProductType.SHOES,
                ProductGender.MALE,
                ProductSizeShoes.M,
                ProductSizes.M,
                ProductSizePantsWaist.SELECT_SIZE,
                ProductSizePantsInseam.SELECT_SIZE,
                "This is a pair of shoes",
                R.drawable.product4.toString(),
                "3"
            )
        )
        productList.add(
            Product(
                ProductType.SHIRTS,
                ProductGender.MALE,
                ProductSizeShoes.SELECT_SIZE,
                ProductSizes.M,
                ProductSizePantsWaist.M,
                ProductSizePantsInseam.M,
                "This is a long sleeve shirt",
                R.drawable.product5.toString(),
                "6"
            )
        )
        productList.add(
            Product(
                ProductType.PANTS,
                ProductGender.MALE,
                ProductSizeShoes.SELECT_SIZE,
                ProductSizes.M,
                ProductSizePantsWaist.M,
                ProductSizePantsInseam.M,
                "This is a pair of pants",
                R.drawable.product3.toString(),
                "4"
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
                R.drawable.product6.toString(),
                "7"
            )
        )
        productList.add(
            Product(
                ProductType.JACKET_BLAZER,
                ProductGender.MALE,
                ProductSizeShoes.SELECT_SIZE,
                ProductSizes.M,
                ProductSizePantsWaist.M,
                ProductSizePantsInseam.M,
                "This is a jacket",
                R.drawable.product9.toString(),
                "10"
            )
        )
        productList.add(
            Product(
                ProductType.DRESS,
                ProductGender.FEMALE,
                ProductSizeShoes.SELECT_SIZE,
                ProductSizes.XS,
                ProductSizePantsWaist.XS,
                ProductSizePantsInseam.XS,
                "This is a beautiful dress",
                R.drawable.product1.toString(),
                "1"
            )
        )
        productList.add(
            Product(
                ProductType.CASUAL_WEAR,
                ProductGender.FEMALE,
                ProductSizeShoes.SELECT_SIZE,
                ProductSizes.XS,
                ProductSizePantsWaist.XS,
                ProductSizePantsInseam.XS,
                "This is a beautiful sweater",
                R.drawable.product16.toString(),
                "17"
            )
        )
        productList.add(
            Product(
                ProductType.CASUAL_WEAR,
                ProductGender.FEMALE,
                ProductSizeShoes.SELECT_SIZE,
                ProductSizes.XS,
                ProductSizePantsWaist.XS,
                ProductSizePantsInseam.XS,
                "This is a beautiful sweater vest",
                R.drawable.product17.toString(),
                "18"
            )
        )
        productList.add(
            Product(
                ProductType.SUITS,
                ProductGender.FEMALE,
                ProductSizeShoes.SELECT_SIZE,
                ProductSizes.XS,
                ProductSizePantsWaist.XS,
                ProductSizePantsInseam.XS,
                "This is a nice suit",
                R.drawable.product9.toString(),
                "10"
            )
        )
        productList.add(
            Product(
                ProductType.ACCESSORIES,
                ProductGender.FEMALE,
                ProductSizeShoes.SELECT_SIZE,
                ProductSizes.XS,
                ProductSizePantsWaist.XS,
                ProductSizePantsInseam.XS,
                "This is a handbag",
                R.drawable.product12.toString(),
                "655b46baa82ef869be176174"
            )
        )
        productList.add(
            Product(
                ProductType.ACCESSORIES,
                ProductGender.FEMALE,
                ProductSizeShoes.SELECT_SIZE,
                ProductSizes.XS,
                ProductSizePantsWaist.XS,
                ProductSizePantsInseam.XS,
                "This is a pair of gloves",
                R.drawable.product18.toString(),
                "19"
            )
        )
        productList.add(
            Product(
                ProductType.ACCESSORIES,
                ProductGender.FEMALE,
                ProductSizeShoes.SELECT_SIZE,
                ProductSizes.XS,
                ProductSizePantsWaist.XS,
                ProductSizePantsInseam.XS,
                "This is a scarf",
                R.drawable.product13.toString(),
                "14"
            )
        )
        productList.add(
            Product(
                ProductType.ACCESSORIES,
                ProductGender.FEMALE,
                ProductSizeShoes.SELECT_SIZE,
                ProductSizes.XS,
                ProductSizePantsWaist.XS,
                ProductSizePantsInseam.XS,
                "This is a tie",
                R.drawable.product7.toString(),
                "8"
            )
        )
        productList.add(
            Product(
                ProductType.ACCESSORIES,
                ProductGender.FEMALE,
                ProductSizeShoes.SELECT_SIZE,
                ProductSizes.XS,
                ProductSizePantsWaist.XS,
                ProductSizePantsInseam.XS,
                "This is a belt",
                R.drawable.product8.toString(),
                "9"
            )
        )

    }
    init {

        // Adding Users to UserList
        userList.add(
            User(
                userFirstName = "John",
                userLastName = "Smith",
                userEmail = "hiJohn123@gmail.com",
                UserRole.ADMIN,
                userId = "1"
            )
        )
        userList.add(
            User(
                userFirstName = "Alex",
                userLastName = "Brown",
                userEmail = "AlexB@gmail.com",
                UserRole.STANDARD,
                userId = "2"
            )
        )
        userList.add(
            User(
                userFirstName = "Jason",
                userLastName = "Ni",
                userEmail = "JNi@gmail.com",
                UserRole.ADMIN,
                userId = "3"
            )
        )
        userList.add(
            User(
                userFirstName = "Kim",
                userLastName = "Johnson",
                userEmail = "KimJohnson@gmail.com",
                UserRole.ADMIN,
                userId = "4"
            )
        )
        userList.add(
            User(
                userFirstName = "Ellen",
                userLastName = "Jones",
                userEmail = "Ellen_J@gmail.com",
                UserRole.STANDARD,
                userId = "5"
            )
        )
        userList.add(
            User(
                userFirstName = "Taylor",
                userLastName = "Wright",
                userEmail = "TayW265@gmail.com",
                UserRole.STANDARD,
                userId = "6"
            )
        )
    }

    fun loadProducts(): List<Product> {
        return productList
    }

    fun loadUsers(): List<User> {
        return userList
    }
}