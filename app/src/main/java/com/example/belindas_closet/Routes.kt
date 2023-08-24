package com.example.belindas_closet

sealed class Routes (val route: String) {
    object Home: Routes("Home")
    object ProductDetail: Routes("Product Detail")
    object Login: Routes("Login")
    object SignUp: Routes("SignUp")
    object AddProduct: Routes("Add Product")
}