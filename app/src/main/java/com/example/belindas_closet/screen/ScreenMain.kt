package com.example.belindas_closet.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
        composable(Routes.ForgotPassword.route) {
            ForgotPasswordPage(navController = navController)
        }
        composable(Routes.ResetPassword.route) {
            ResetPasswordPage(navController = navController)
        }
        composable(Routes.AdminView.route) {
            AdminView(navController = navController)
        }

        composable(Routes.CreatorView.route) {
            CreatorView(navController = navController)
        }

        composable(Routes.IndividualProduct.route+"/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            IndividualProductPage(navController = navController,
                productId = backStackEntry.arguments!!.getString("productId")!!,
            )
        }

        composable(Routes.IndividualProductUpdatePage.route+"/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            IndividualProductUpdatePage(navController = navController,
                productId = backStackEntry.arguments!!.getString("productId")!!,
            )
        }
    }
}