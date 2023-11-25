package com.example.belindas_closet.screen


import android.content.res.Resources
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.belindas_closet.MainActivity
import com.example.belindas_closet.R
import com.example.belindas_closet.Routes
import com.example.belindas_closet.data.Datasource
import com.example.belindas_closet.model.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailPage(navController: NavController) {
    var drawerState by remember { mutableStateOf(DrawerValue.Closed) }

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
                            navController.navigate(Routes.Home.route)
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
                            //TODO: verify that the user is an admin or the owner of the product
                            //If yes, then navigate to the update page
                            navController.navigate(Routes.Update.route)
                            //Else, navigate to the login page
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
        },
    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)
        Column(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomTextField(text = MainActivity.getProductType().lowercase().replaceFirstChar { it.uppercase() })
            ProductDetailList(products = Datasource().loadProducts(), navController = navController)
        }
    }
}

@Composable
fun ProductDetailCard(product: Product, navController: NavController) {
    val density = Resources.getSystem().displayMetrics.density
    Card(
        modifier = Modifier
            .padding(8.dp)
            .widthIn(0.dp, (Resources.getSystem().displayMetrics.widthPixels * .8/density).dp)
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
            if (product.productType.type != "Shoes") {
                Text(text = "Size: ${product.productSizes}")
            } else {
                Text(text = "Size: ${product.productSizeShoe.size}")
            }
            Text(text = "Description: ${product.productDescription}",
                maxLines = 2,
                overflow = TextOverflow.Ellipsis)
            TextButton(
                onClick = {
                    navController.navigate(Routes.IndividualProduct.route+"/${product.id}")
                }
            ) {
                Text(text = "More Info")
            }
        }
    }
}

@Composable
fun ProductDetailList(products: List<Product>, navController: NavController) {
    val hidden = MainActivity.getPref().getStringSet("hidden", setOf(""))
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(products
            .filter { it.productType.type == MainActivity.getProductType() }
            .filter { !hidden!!.contains(it.productType.name) }) { product ->
            ProductDetailCard(product = product, navController = navController)
        }
    }
}


