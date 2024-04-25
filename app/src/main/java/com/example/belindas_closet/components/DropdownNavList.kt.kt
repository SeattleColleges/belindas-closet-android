package com.example.belindas_closet.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.belindas_closet.MainActivity
import com.example.belindas_closet.Routes
import com.example.belindas_closet.model.ProductType

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun DropdownNavList(drawerState: DrawerValue, navController: NavController, onDismissRequest: () -> Unit = {}) {
    DropdownMenu(
        drawerState == DrawerValue.Open,
        onDismissRequest = onDismissRequest,
        modifier = Modifier
            .padding(8.dp)
    ) {
        DropdownMenuItem(
            text = { Text(text = "Mission") },
            onClick = {
                onDismissRequest()
                navController.navigate(
                    Routes.Mission.route)
            }
        )
        ProductType.values().forEach { productType ->
            DropdownMenuItem(
                text = { Text(productType.type) },
                onClick = {
                    onDismissRequest()
                    // navigate to product type page
                    MainActivity.setProductType(productType.type)
                    navController.navigate(
                        Routes.ProductDetail.route)
                }
            )
        }
    }
}