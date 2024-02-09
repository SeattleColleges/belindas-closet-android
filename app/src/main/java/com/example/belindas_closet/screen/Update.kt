package com.example.belindas_closet.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.belindas_closet.MainActivity
import com.example.belindas_closet.R
import com.example.belindas_closet.Routes
import com.example.belindas_closet.data.Datasource
import com.example.belindas_closet.data.network.dto.auth_dto.Role
import com.example.belindas_closet.model.Product
import kotlinx.coroutines.launch

//TODO Add Product Categories to Navbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePage(navController: NavController) {
    var hiddenProducts by remember { mutableStateOf(setOf<String>()) }
    var drawerState by remember { mutableStateOf(DrawerValue.Closed) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            /* Back arrow that navigates back to login page */
            TopAppBar(
                title = { Text("Product Detail") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Routes.ProductDetail.route)
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back to Product Detail")
                    }
                },
                actions =
                {
                    /*  Edit option visibility */
                    val userRole = MainActivity.getPref().getString("userRole", Role.USER.name)?.let {
                        Role.valueOf(it)
                    } ?: Role.USER
                    if (userRole == Role.ADMIN || userRole == Role.CREATOR) {
                        IconButton(
                            onClick = {
                                navController.navigate(Routes.IndividualProductUpdatePage.route)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit"
                            )
                        }
                    }
                    IconButton(
                        onClick = {
                            drawerState = DrawerValue.Open
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu"
                        )
                    }
                    DropDownCategoryList(drawerState, navController) {
                        drawerState = DrawerValue.Closed
                    }
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // TODO: verify the user is logged in
                    // if logged in, navigate to AddProduct page
                    navController.navigate(Routes.AddProduct.route)
                    // else, navigate to Login page
                },
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Create Product")
            }
        }
    ) {
        innerPadding ->
        val modifier = Modifier.padding(innerPadding)
        Column (
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            CustomTextField(text = stringResource(R.string.update_page_title))

            hiddenProducts = MainActivity.getPref().getStringSet("hidden", setOf()) ?: setOf()
            UpdateProductList(products = Datasource().loadProducts(), hiddenProducts = hiddenProducts, navController = navController)
        }
    }
}

@Composable
fun UpdateProductCard(product: Product, navController: NavController) {
    var isDelete by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val current = LocalContext.current

    Card(
        modifier = Modifier
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
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
            Text(text = "Description: ${product.productDescription}")
            Row(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = {
                    isDelete = !isDelete
                }) {
                    Icon(
                        imageVector = Icons.Default.Delete, contentDescription = "Delete"
                    )
                }
                if (isDelete) {
                    ConfirmationDialog(onConfirm = {
                        coroutineScope.launch {
                            val isDeleteSuccessful = delete(product.id, navController, current)
                            if (isDeleteSuccessful) {
                                val hidden = MainActivity.getPref().getStringSet("hidden", mutableSetOf(product.id))
                                hidden?.add(product.id)
                                val editor = MainActivity.getPref().edit()
                                editor.putStringSet("hidden", hidden)
                                editor.apply()
                            }
                        }
                        // Remove the product from the database
                        isDelete = false
                    }, onDismiss = {
                        isDelete = false
                    })
                }
            }
        }
    }
}

@Composable
fun UpdateProductList(products: List<Product>, hiddenProducts: Set<String>, navController: NavController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(products
            .filter { it.productType.type == MainActivity.getProductType() }
            .filter { !hiddenProducts.contains(it.id) }) { product ->
            UpdateProductCard(product = product, navController = navController)
        }
    }
}

@Composable
fun ConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = {
        onDismiss()
    }) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .border(1.dp, Color.White)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(stringResource(R.string.update_delete_confirm_text))
                Spacer(modifier = Modifier.padding(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { onDismiss() }, modifier = Modifier.padding(8.dp)
                    ) {
                        Text("No")
                    }
                    Button(
                        onClick = { onConfirm() }, modifier = Modifier.padding(8.dp)
                    ) {
                        Text("Yes")
                    }
                }
            }
        }
    }
}