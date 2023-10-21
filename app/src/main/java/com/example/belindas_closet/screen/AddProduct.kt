package com.example.belindas_closet.screen


import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.belindas_closet.Routes
import com.example.belindas_closet.model.Product
import com.example.belindas_closet.model.Sizes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductPage(navController: NavHostController) {
    var productName by remember { mutableStateOf("") }
    var productDescription by remember { mutableStateOf("") }
    var productSize by remember { mutableStateOf(Sizes.SELECT_SIZE) } /* Default size set */
    val productImage by remember { mutableStateOf("") }
    var toastMessage by remember { mutableStateOf("") }
    /* Back arrow that navigates back to login page */
    TopAppBar(
        title = { Text("Login") }, /* todo: change destination where arrow navigates to */
        navigationIcon = {
            IconButton(
                onClick = {
                    navController.navigate(Routes.Login.route) /* Navigate back to login page */
                }
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProductInfoField(
            productName = productName,
            onProductChange = { newName -> productName = newName }
        )

        ProductDescriptionField(
            productDescription = productDescription,
            onDescriptionChange = { newDescription -> productDescription = newDescription }
        )

        ProductSizeField(
            productSize = productSize,
            onSizeChange = { newSize -> productSize = newSize }
        )

        /* TODO: finish up product button and validation logic */
        Button(
            onClick = {
                if (productName.isNotEmpty()
                    && productDescription.isNotEmpty()
                    && productSize != Sizes.SELECT_SIZE) {
                    val newProduct = Product(productName, productDescription, productSize, productImage)
                    // TODO: Save the new product to the database or use a list to hold products
                    // Set the toast message for success
                    toastMessage = "Product added successfully"
                } else {
                    /* TODO: show error message for empty fields */
                    // Set the toast message for an error
                    toastMessage = "Please fill in all fields"
                }
            },
            modifier = Modifier
                .padding(16.dp)
                .width(200.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Add Product")
        }
    }
    // Display the toast message
    if (toastMessage.isNotEmpty()) {
        Toast.makeText(
            LocalContext.current,
            toastMessage,
            Toast.LENGTH_SHORT
        ).show()
    }
}

@Composable
fun ProductInfoField(productName: String, onProductChange: (String) -> Unit) {
    TextField(
        value = productName,
        onValueChange = onProductChange,
        label = { Text(text = "Product name") },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
fun ProductDescriptionField(productDescription: String, onDescriptionChange: (String) -> Unit) {
    TextField(
        value = productDescription,
        onValueChange = onDescriptionChange,
        label = { Text(text = "Product Description") },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
fun ProductSizeField(productSize: Sizes, onSizeChange: (Sizes) -> Unit) {
    val sizes = Sizes.values() /* using enum values directly */
    var selectedSize by remember { mutableStateOf(productSize) }
    var isDropdownMenuExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isDropdownMenuExpanded = !isDropdownMenuExpanded }
    ) {
        Text(
            text = "Size: ${selectedSize.name}",
            modifier = Modifier.padding(16.dp)
        )
        DropdownMenu(
            expanded = isDropdownMenuExpanded,
            onDismissRequest = { isDropdownMenuExpanded = false }
        ) {
            sizes.forEach { size ->
                DropdownMenuItem(
                    text = { Text(size.name) },
                    onClick = {
                        selectedSize = size
                        isDropdownMenuExpanded = false
                        onSizeChange(size) /* passing selected size using callback*/
                    }
                )
            }
        }

    }
}