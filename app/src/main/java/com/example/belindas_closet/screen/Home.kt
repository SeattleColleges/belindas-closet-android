package com.example.belindas_closet.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.belindas_closet.R
import com.example.belindas_closet.Routes
import com.example.belindas_closet.data.Product
import com.example.belindas_closet.data.Sizes

@Composable
fun HomePage(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.packaging),
            contentDescription = stringResource(id = R.string.home_logo_description),
            modifier = Modifier
                .size(100.dp)
                .padding(16.dp)
        )
        CustomTextField(
            text = stringResource(R.string.home_welcome),
            fontSize = 30.sp
        )
        NavigateButtons(
            navController = navController,
            text = stringResource(R.string.home_login)
        )
        Spacer(modifier = Modifier.padding(8.dp))

        // Display a list of product from Product class
        val products = listOf(
            // TODO: Replace these products with the products from the database
            // Examples of products:
            Product("Dresses", "Dresses", Sizes.S, R.drawable.product1.toString()),
            Product("Shirts", "Shirts", Sizes.M, R.drawable.product2.toString()),
            Product("Pants", "Pants", Sizes.L, R.drawable.product3.toString()),
            Product("Shoes", "Shoes", Sizes.XL, R.drawable.product4.toString()),
        )
        ProductList(products = products, navController = navController)
    }
}

@Composable
fun CustomTextField(text: String, fontSize: TextUnit = 20.sp) {
    Text(
        text = text,
        style = TextStyle(
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Default,
        ),
        modifier = Modifier
            .wrapContentSize()
    )
}

@Composable
fun ProductCard(product: Product, navController: NavController) {
    Card(
        modifier = Modifier
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
                painter = painterResource(id = product.image.toInt()),
                contentDescription = stringResource(id = R.string.product_image_description),
                modifier = Modifier
                    .size(200.dp)
                    .padding(16.dp),

                )
            Text(
                text = "Name: ${product.name}",
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Default,
                ),
                modifier = Modifier
                    .wrapContentSize()
            )
        }
    }
}

@Composable
fun ProductList(products: List<Product>, navController: NavController) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(products) { product ->
            ProductCard(product = product, navController = navController)
            Spacer(modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun NavigateButtons(navController: NavController, text: String) {
    Button(
        onClick = {
            navController.navigate(Routes.Login.route)
        },
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default,
            )
        )
    }
}