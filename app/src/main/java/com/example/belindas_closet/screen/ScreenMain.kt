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
        composable(Routes.ProductDetail.route+"/{category}",
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ) { backStackEntry ->
                ProductDetailPage(navController = navController,
                categoryKey = backStackEntry.arguments!!.getString("category")!!)
        }
        composable(Routes.Update.route+"/{category}",
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ) { backStackEntry ->
                UpdatePage(navController = navController,
                categoryKey = backStackEntry.arguments!!.getString("category")!!)
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

        composable(Routes.IndividualProduct.route+"/{category}"+"/{productId}",
            arguments = listOf(
                navArgument("category") { type = NavType.StringType },
                navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            IndividualProductPage(navController = navController,
                category = backStackEntry.arguments!!.getString("category")!!,
                productId = backStackEntry.arguments!!.getString("productId")!!,
            )
        }

        composable(Routes.IndividualProductUpdate.route+"/{category}"+"/{productId}",
            arguments = listOf(
                navArgument("category") { type = NavType.StringType },
                navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            IndividualProductUpdatePage(navController = navController,
                category = backStackEntry.arguments!!.getString("category")!!,
                productId = backStackEntry.arguments!!.getString("productId")!!,
            )
        }
    }
}