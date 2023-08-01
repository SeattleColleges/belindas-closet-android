package com.example.belindas_closet

sealed class Routes (val route: String) {
    object Login: Routes("Login")
    object SignUp: Routes("SignUp")
    object AddProduct: Routes("Add Product")
}