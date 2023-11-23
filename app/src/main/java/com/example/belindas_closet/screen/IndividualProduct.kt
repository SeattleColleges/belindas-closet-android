package com.example.belindas_closet.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.belindas_closet.R
import com.example.belindas_closet.Routes
import com.example.belindas_closet.data.Datasource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.window.Popup

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IndividualProductPage(navController: NavController, productId: String) {

    var drawerState by remember { mutableStateOf(DrawerValue.Closed) }

    TopAppBar(
        title = { Text("Back") },
        navigationIcon = {
            IconButton(
                onClick = {
                    navController.navigate(Routes.ProductDetail.route)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back to Product page"
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    navController.navigate(Routes.IndividualProductUpdatePage.route + "/$productId")
                }
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
            }
            IconButton(
                onClick = {
                    drawerState = DrawerValue.Open
                }
            ) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
            }
        }
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, top = 100.dp, end = 10.dp, bottom = 50.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var expandedState by remember { mutableStateOf(false) }
        val product = Datasource().loadProducts().find { it.id == productId }!!
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card {
                Box(
                    contentAlignment = Alignment.BottomEnd,
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable {
                            expandedState = true
                        }
                ) {
                    Image(
                        painter = painterResource(id = product.productImage.toInt()),
                        contentDescription = stringResource(id = R.string.product_image_description),
                        modifier = Modifier
                            .size(200.dp)
                    )
                    Icon(Icons.Filled.Search, contentDescription = "Enlarge Image Icon")
                }
            }
            Text(
                text = "Size: ${product.productSizes}",
                modifier = Modifier.padding(vertical = 10.dp)
            )
            Text(
                text = "Gender: ${product.productGender}",
                modifier = Modifier.padding(vertical = 10.dp)
            )
            if (product.productType.type == "Pant") {
                Text(
                    text = "Waist: ${product.productSizePantsWaist.size}",
                    modifier = Modifier.padding(vertical = 10.dp)
                )
                Text(
                    text = "Inseam: ${product.productSizePantsInseam.size}",
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            } else if (product.productType.type == "Shoes") {
                Text(
                    text = "Shoes Size: ${product.productSizeShoe.size}",
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
            Text(
                text = "Description: ${product.productDescription}",
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }
        if (expandedState) {
            Popup {
                Card(
                    colors = CardColors(
                        Color(125, 125, 125, 125),
                        Color.LightGray,
                        Color.LightGray,
                        Color.LightGray
                    ),
                    shape = RectangleShape,
                    modifier = Modifier.clickable {
                        expandedState = false
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = product.productImage.toInt()),
                            contentDescription = stringResource(id = R.string.product_image_description),
                            alignment = Alignment.Center,
                            modifier = Modifier
                                .size(350.dp)
                        )
                    }
                }
            }
        }
    }
}