package com.example.belindas_closet.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.belindas_closet.MainActivity
import com.example.belindas_closet.R
import com.example.belindas_closet.Routes
import com.example.belindas_closet.data.Datasource
import com.example.belindas_closet.model.Product
import com.example.belindas_closet.model.ProductSizes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePage(navController: NavController) {
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
                actions = {
                    IconButton(
                        onClick = {
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                    }
                })
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

            UpdateProductList(products = Datasource().loadProducts(), navController = navController)
        }
    }
}

@Composable
fun TextFieldEditable(
    initialDescription: String, initialSize: ProductSizes
) {
    var updateDescription by remember { mutableStateOf(initialDescription) }
    var selectedSize by remember { mutableStateOf(initialSize) }
    var isDropdownMenuExpanded by remember { mutableStateOf(false) }
    val sizes = ProductSizes.values()

    TextField(
        value = updateDescription,
        onValueChange = { newDescription ->
            updateDescription = newDescription
        },
        label = { Text("Description") },
        singleLine = true,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
    )

    Box(modifier = Modifier
        .fillMaxWidth()
        .clickable { isDropdownMenuExpanded = !isDropdownMenuExpanded }) {
        Text(
            text = "Size: ${selectedSize.name}", modifier = Modifier.padding(16.dp)
        )
        DropdownMenu(expanded = isDropdownMenuExpanded,
            onDismissRequest = { isDropdownMenuExpanded = false }) {
            sizes.forEach { size ->
                DropdownMenuItem(text = { Text(size.name) }, onClick = {
                    selectedSize = size
                    isDropdownMenuExpanded = false
                })
            }
        }
    }

    //TODO: Add image field, can be called from the AddProduct.kt
}

@Composable
fun UpdateProductCard(product: Product, navController: NavController) {
    var isEditing by remember { mutableStateOf(true) }
    var isDelete by remember { mutableStateOf(false) }
    var isSave by remember { mutableStateOf(false) }
    var isCancel by remember { mutableStateOf(false) }
    var isVisible by rememberSaveable { mutableStateOf(true) }

    AnimatedVisibility(visible = isVisible ) {
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

                // Display the text fields and buttons
                if (isEditing) {
                    TextFieldEditable(
                        initialDescription = product.productDescription,
                        initialSize = product.productSizes
                    )
                    Row {
                        Button(onClick = {
                            isCancel = !isCancel
                        }) {
                            Icon(
                                imageVector = Icons.Default.Close, contentDescription = "Cancel"
                            )
                        }
                        if (isCancel) {
                            ConfirmCancelDialog(onConfirm = {
                                // TODO: Cancel the edit
                                // Keep the original data
                                isCancel = false
                            }, onDismiss = {
                                isCancel = false
                            }, navController = navController)
                        }

                        Spacer(modifier = Modifier.padding(8.dp))
                        Button(onClick = {
                            isSave = !isSave
                        }) {
                            Icon(
                                imageVector = Icons.Default.Check, contentDescription = "Save"
                            )
                        }
                        if (isSave) {
                            ConfirmSaveDialog(onConfirm = {
                                // TODO: Save the product to the database
                                // Update the product with the new data
                                isSave = false
                            }, onDismiss = {
                                isSave = false
                            })
                        }
                    }
                } else {

                    // Display the text fields and buttons
                        if (isEditing) {
                            TextFieldEditable(
                                initialDescription = product.productDescription,
                                initialSize = product.productSizes
                            )
                            Row {
                                Button(onClick = {
                                    isCancel = !isCancel
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Cancel"
                                    )
                                }
                                if (isCancel) {
                                    ConfirmCancelDialog(onConfirm = {
                                        // TODO: Cancel the edit
                                        // Keep the original data
                                        isCancel = false
                                    }, onDismiss = {
                                        isCancel = false
                                    }, navController = navController)
                                }

                                Spacer(modifier = Modifier.padding(8.dp))
                                Button(onClick = {
                                    isSave = !isSave
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Save"
                                    )
                                }
                                if (isSave) {
                                    ConfirmSaveDialog(onConfirm = {
                                        // TODO: Save the product to the database
                                        // Update the product with the new data
                                        isSave = false
                                    }, onDismiss = {
                                        isSave = false
                                    })
                                }
                            }
                        } else {
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
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete"
                                    )
                                }
                                if (isDelete) {
                                    ConfirmationDialog(onConfirm = {
                                        val hidden = MainActivity.getPref()
                                            .getStringSet("hidden", mutableSetOf(product.productType.name))
                                        hidden?.add(product.productType.name)
                                        val editor = MainActivity.getPref().edit()
                                        editor.putStringSet("hidden", hidden)
                                        editor.apply()
                                        isVisible = false
                                        // TODO: Delete the product from the database
                                        // Remove the product from the database
                                        isDelete = false
                                    }, onDismiss = {
                                        isDelete = false
                                    })
                                }
                                Spacer(modifier = Modifier.padding(16.dp))
                                Button(onClick = {
                                    isEditing = !isEditing
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Edit"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

@Composable
fun UpdateProductList(products: List<Product>, navController: NavController) {
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

@Composable
fun ConfirmSaveDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = {
        onDismiss()
    }) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .border(1.dp, Color.White),
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(stringResource(R.string.update_save_confirm_text))
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

@Composable
fun ConfirmCancelDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    navController: NavController
) {
    Dialog(onDismissRequest = {
        onDismiss()
    }) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .border(1.dp, Color.White),
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(stringResource(R.string.update_cancel_confirm_text))
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
                        onClick = { onConfirm()
                            navController.navigate(Routes.ProductDetail.route)
                                  }, modifier = Modifier.padding(8.dp)
                    ) {
                        Text("Yes")
                    }
                }
            }
        }
    }
}