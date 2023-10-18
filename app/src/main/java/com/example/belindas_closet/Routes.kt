package com.example.belindas_closet

sealed class Routes (val route: String) {
    object Home: Routes("Home")
    object ProductDetail: Routes("Product Detail")
    object Update: Routes("Update")
    object Login: Routes("Login")
    object SignUp: Routes("SignUp")
    object AddProduct: Routes("Add Product")

    object IndividualProduct: Routes("Individual_Product")
    object IndividualProductUpdate: Routes("Individual_Product_Update")
}