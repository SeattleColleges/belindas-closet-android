package com.example.belindas_closet.screen

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.belindas_closet.data.Datasource
import com.example.belindas_closet.model.Product
import com.example.belindas_closet.model.Sizes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IndividualProductUpdatePage(navController: NavController, productId: String) {
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
                            navController.navigate(Routes.IndividualProduct.route+"/${productId}")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back to Home page"
                        )
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
            val product = Datasource().loadProducts().find { it.name == productId }!!
            UpdateIndividualProductCard(product = product, navController = navController)
        }
    }
}

@Composable
fun UpdateIndividualProductCard(product: Product, navController: NavController) {
    var isEditing by remember { mutableStateOf(false) }
    var isDelete by remember { mutableStateOf(false) }
    var isSave by remember { mutableStateOf(false) }
    var isCancel by remember { mutableStateOf(false) }

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
                painter = painterResource(id = product.image.toInt()),
                contentDescription = stringResource(id = R.string.product_image_description),
                modifier = Modifier
                    .size(200.dp)
                    .padding(16.dp),
            )
            Text(
                text = "Name: ${product.name}", style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Default,
                ), modifier = Modifier.wrapContentSize()
            )
            Text(text = "Size: ${product.size}")
            Text(text = "Description: ${product.description}")

            // Display the text fields and buttons
            if (isEditing) {
                TextFieldEditableIndividual(
                    initialName = product.name,
                    initialDescription = product.description,
                    initialSize = product.size
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
                            imageVector = Icons.Default.Edit, contentDescription = "Edit"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TextFieldEditableIndividual(
    initialName: String, initialDescription: String, initialSize: Sizes
) {
    var updateName by remember { mutableStateOf(initialName) }
    var updateDescription by remember { mutableStateOf(initialDescription) }
    var selectedSize by remember { mutableStateOf(initialSize) }
    var isDropdownMenuExpanded by remember { mutableStateOf(false) }
    val sizes = Sizes.values()

    TextField(
        value = updateName,
        onValueChange = { editName ->
            updateName = editName
        },
        label = { Text("Name") },
        singleLine = true,
        modifier = Modifier
            .padding(8.dp)
    )

    TextField(
        value = updateDescription,
        onValueChange = { newDescription ->
            updateDescription = newDescription
        },
        label = { Text("Description") },
        singleLine = true,
        modifier = Modifier
            .padding(8.dp)
    )

    Box(modifier = Modifier
        .fillMaxWidth()
        .clickable { isDropdownMenuExpanded = !isDropdownMenuExpanded }) {
        Text(
            text = "Size: ${selectedSize.name}", modifier = Modifier.padding(horizontal = 48.dp, vertical = 16.dp)
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