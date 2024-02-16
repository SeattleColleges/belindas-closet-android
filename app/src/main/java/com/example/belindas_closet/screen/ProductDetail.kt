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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import com.example.belindas_closet.data.network.dto.auth_dto.Role
import com.example.belindas_closet.model.Product
import com.example.belindas_closet.model.ProductType

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
                    /*  Edit option visibility */
                    val userRole = MainActivity.getPref().getString("userRole", Role.USER.name)?.let {
                        Role.valueOf(it)
                    } ?: Role.USER
                    if (userRole == Role.ADMIN || userRole == Role.CREATOR) {
                        IconButton(
                            onClick = {
                                navController.navigate(Routes.Update.route)
                            }
                        ) {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                        }
                    }
                    IconButton(
                        onClick = {
                            drawerState = DrawerValue.Open
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                    }
                    DropDownCategoryList(drawerState, navController) {
                        drawerState = DrawerValue.Closed
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
            .widthIn(0.dp, (Resources.getSystem().displayMetrics.widthPixels * .8 / density).dp)
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
            .filter { !hidden!!.contains(it.id) }) { product ->
            ProductDetailCard(product = product, navController = navController)
        }
    }
}
@Composable
fun DropDownCategoryList(drawerState: DrawerValue, navController: NavController, onDismissRequest: () -> Unit = {}) {
    DropdownMenu(
        drawerState == DrawerValue.Open,
        onDismissRequest = onDismissRequest,
        modifier = Modifier
            .padding(8.dp)
    ) {
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

