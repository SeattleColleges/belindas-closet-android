package com.example.belindas_closet.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.belindas_closet.R
import com.example.belindas_closet.Routes
import com.example.belindas_closet.model.Product
import com.example.belindas_closet.data.Datasource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IndividualProductPage(navController: NavController, productId: String) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            /* Back arrow that navigates back to login page */
            TopAppBar(
                title = { Text("Home") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigate(Routes.ProductDetail.route)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back to Home page"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate(Routes.IndividualProductUpdate.route+"/${productId}")
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                    }
                }
            )
        },
    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)
        Column(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomTextField(text = productId)
            val product = Datasource().loadProducts().find { it.productType.name == productId }!!
            IndividualProductCard(product = product, navController = navController)
        }
    }
}

@Composable
fun IndividualProductCard(product: Product, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                navController.navigate(Routes.ProductDetail.route)
            },
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = product.productImage.toInt()),
                contentDescription = stringResource(id = R.string.product_image_description),
                modifier = Modifier
                    .size(200.dp)
                    .padding(16.dp),
            )
            Text(text = "Size: ${product.productSizes}")
            Text(text = "Gender: ${product.productGender}")
            if (product.productType.type == "Pant") {
                Text(text = "Waist: ${product.productSizePantsWaist.size}")
                Text(text = "Inseam: ${product.productSizePantsInseam.size}")
            } else if (product.productType.type == "Shoes") {
                Text(text = "Shoes Size: ${product.productSizeShoe.size}")
            }
            Text(text = "Description: ${product.productDescription}")
        }
    }
}

