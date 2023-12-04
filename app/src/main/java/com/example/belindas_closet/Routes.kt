package com.example.belindas_closet

sealed class Routes (val route: String) {
    object Home: Routes("Home")
    object ProductDetail: Routes("Product Detail")
    object Update: Routes("Update")
    object Login: Routes("Login")
    object SignUp: Routes("SignUp")
    object AddProduct: Routes("Add Product")
    object ForgotPassword: Routes("Forgot Password")
    object IndividualProduct: Routes("Individual_Product")
    object IndividualProductUpdatePage: Routes("Individual_Product_Update")
    object AdminView: Routes("Admin_View")
    object DonationInfo: Routes("Donation_Info")
}