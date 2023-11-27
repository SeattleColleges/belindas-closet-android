package com.example.belindas_closet.screen

import android.content.Context
import android.net.http.HttpException
import android.widget.Toast
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
import com.example.belindas_closet.data.network.auth.ArchiveService
import com.example.belindas_closet.data.network.auth.DeleteService
import com.example.belindas_closet.data.network.dto.auth_dto.ArchiveRequest
import com.example.belindas_closet.data.network.dto.auth_dto.DeleteRequest
import com.example.belindas_closet.data.network.dto.auth_dto.Role
import com.example.belindas_closet.model.Product
import com.example.belindas_closet.model.ProductSizes
import kotlinx.coroutines.launch

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
            val product = Datasource().loadProducts().find { it.id == productId }!!
            UpdateIndividualProductCard(product = product, navController = navController)
        }
    }
}

@Composable
fun UpdateIndividualProductCard(product: Product, navController: NavController) {
    var isEditing by remember { mutableStateOf(false) }
    var isDelete by remember { mutableStateOf(false) }
    var isArchive by remember { mutableStateOf(false) }
    var isSave by remember { mutableStateOf(false) }
    var isCancel by remember { mutableStateOf(false) }
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

            // Display the text fields and buttons
            if (isEditing) {
                TextFieldEditableIndividual(
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
                        ConfirmCancelDialogIndividual(onConfirm = {
                            // TODO: Cancel the edit
                            // Keep the original data
                            isCancel = false
                        }, onDismiss = {
                            isCancel = false
                        }, navController = navController,
                            productId = product.id)
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
                        ConfirmSaveDialogIndividual(onConfirm = {
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
                        ConfirmationDialogIndividual(onConfirm = {
                            coroutineScope.launch {
                                val isDeleteSuccessful = delete(product.id, navController, current)
                                if (isDeleteSuccessful) {
                                    val hidden = MainActivity.getPref().getStringSet("hidden", mutableSetOf(product.id))
                                    hidden?.add(product.id)
                                    val editor = MainActivity.getPref().edit()
                                    editor.putStringSet("hidden", hidden)
                                    editor.apply()
                                    navController.navigate(Routes.ProductDetail.route)
                                }
                            }
                            // Remove the product from the database
                            isDelete = false
                        }, onDismiss = {
                            isDelete = false
                        })
                    }
                    Spacer(modifier = Modifier.padding(14.dp))
                    Button(onClick = {
                        isArchive = !isArchive
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.archive_icon), contentDescription = "Archive",
                            modifier = Modifier
                                .size(20.dp)
                        )
                    }
                    if (isArchive) {
                        ConfirmationArchiveDialogIndividual(onConfirm = {
                            coroutineScope.launch {
                                val isArchiveSuccessful = archive(product.id, navController, current)
                                if (isArchiveSuccessful) {
                                    val hidden = MainActivity.getPref().getStringSet("hidden", mutableSetOf(product.id))
                                    hidden?.add(product.id)
                                    val editor = MainActivity.getPref().edit()
                                    editor.putStringSet("hidden", hidden)
                                    editor.apply()
                                }
                            }
                            isArchive = false
                        }, onDismiss = {
                            isArchive = false
                        })
                    }
                    Spacer(modifier = Modifier.padding(14.dp))
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

@Composable
fun ConfirmationDialogIndividual(
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
fun ConfirmationArchiveDialogIndividual(
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
                Text(stringResource(R.string.update_archive_confirm_text))
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
fun ConfirmSaveDialogIndividual(
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
fun ConfirmCancelDialogIndividual(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    navController: NavController,
    productId: String
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
                            navController.navigate(Routes.IndividualProduct.route+"/${productId}")
                        }, modifier = Modifier.padding(8.dp)
                    ) {
                        Text("Yes")
                    }
                }
            }
        }
    }
}

suspend fun delete(productId: String, navController: NavController, current: Context): Boolean {
    return try {
        val userRole = MainActivity.getPref().getString("userRole", Role.USER.name)?.let {
            Role.valueOf(it) } ?: Role.USER
        val deleteRequest = DeleteRequest(
            id = productId,
            role = Role.ADMIN
        )
        val deleteResponse = DeleteService.create().delete(deleteRequest)
        if (userRole != Role.ADMIN) {
            Toast.makeText(current, R.string.unauthorized_toast, Toast.LENGTH_SHORT).show()
            false
        } else if (productId.count() != 24) {
            Toast.makeText(current, R.string.invalid_id, Toast.LENGTH_SHORT).show()
            false
        } else if (deleteResponse != null) {
            MainActivity.getPref().edit().putBoolean("isHidden", deleteResponse.isHidden).apply()
            navController.navigate(Routes.ProductDetail.route)
            Toast.makeText(current, R.string.delete_successful_toast, Toast.LENGTH_SHORT).show()
            true
        } else {
            Toast.makeText(current, R.string.delete_failed_toast, Toast.LENGTH_SHORT).show()
            false
        }
    } catch (e: HttpException) {
        e.printStackTrace()
        println("Error: ${e.message}")
        Toast.makeText(current, "Delete failed. Error: ${e.message}", Toast.LENGTH_SHORT).show()
        false
    }
}

suspend fun archive(productId: String, navController: NavController, current: Context): Boolean {
    return try {
        val userRole = MainActivity.getPref().getString("userRole", Role.USER.name)?.let {
            Role.valueOf(it) } ?: Role.USER
        val archiveRequest = ArchiveRequest(
            id = productId,
            role = Role.ADMIN
        )
        val archiveResponse = ArchiveService.create().archive(archiveRequest)
        if (userRole != Role.ADMIN) {
            Toast.makeText(current, R.string.unauthorized_toast, Toast.LENGTH_SHORT).show()
            false
        } else if (productId.count() != 24) {
            Toast.makeText(current, R.string.invalid_id_toast, Toast.LENGTH_SHORT).show()
            false
        } else if (archiveResponse != null) {
            MainActivity.getPref().edit().putBoolean("isSold", archiveResponse.isSold).apply()
            navController.navigate(Routes.ProductDetail.route)
            Toast.makeText(current, R.string.archive_successful_toast, Toast.LENGTH_SHORT).show()
            true
        } else {
            Toast.makeText(current, R.string.archive_failed_toast, Toast.LENGTH_SHORT).show()
            false
        }
    } catch (e: HttpException) {
        e.printStackTrace()
        println("Error: ${e.message}")
        Toast.makeText(current, "Archive failed. Error: ${e.message}", Toast.LENGTH_SHORT).show()
        false
    }
}

