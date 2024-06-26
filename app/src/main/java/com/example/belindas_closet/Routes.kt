package com.example.belindas_closet

sealed class Routes (val route: String) {
    object Home: Routes("Home")
    object ProductDetail: Routes("Product Detail")
    object Update: Routes("Update")
    object Login: Routes("Login")
    object SignUp: Routes("SignUp")
    object AddProduct: Routes("Add Product")
    object ForgotPassword: Routes("Forgot Password")
    object ChangePassword: Routes("Change Password")
    object IndividualProduct: Routes("Individual_Product")
    object IndividualProductUpdatePage: Routes("Individual_Product_Update")
    object AdminView: Routes("Admin_View")
    object CreatorView: Routes("Creator_View")
    object EditUserRole: Routes("Edit User Role")
    object DonationInfo: Routes("Donation_Info")
    object Dashboard: Routes("Dashboard")
    object ContactUs: Routes("Contact_Us")
}
