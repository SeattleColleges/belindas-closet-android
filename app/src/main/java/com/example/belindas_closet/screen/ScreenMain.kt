package com.example.belindas_closet.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.belindas_closet.Routes

@Preview(showBackground = true)
@Composable
fun ScreenMain() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Home") {
        composable(Routes.Home.route) {
            HomePage(navController = navController)
        }
        composable(Routes.ProductDetail.route) {
            ProductDetailPage(navController = navController)
        }
        composable(Routes.Update.route) {
            UpdatePage(navController = navController)
        }
        composable(Routes.Login.route) {
            LoginPage(navController = navController)
        }
        composable(Routes.SignUp.route) {
            SignUpPage(navController = navController)
        }
        composable(Routes.AddProduct.route) {
            AddProductPage(navController = navController)
        }
    }
}