package com.example.belindas_closet.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.belindas_closet.data.Product


@Composable
fun AddProductPage(navController: NavHostController) {
    var productName by remember { mutableStateOf("") }
    var productDescription by remember { mutableStateOf("") }
    var productSize by remember { mutableStateOf("") }

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
            onDescriptionChange = { newName -> productDescription = newName }
        )

        ProductSizeField(
            productSize = productSize,
            onSizeChange = { newName -> productSize = newName }
        )

        /* TODO: finish up product button and validation logic */
        Button(
            onClick = {
                if (productName.isNotEmpty()
                    && productDescription.isNotEmpty()
                    && productSize.isNotEmpty()) {
                    val newProduct = Product(productName, productDescription, productSize)
                    /* TODO: save new product to db or use a list to hold products (ex: List<Product>) */
                } else {
                    /* TODO: show error message for empty fields */
                }
            },
            modifier = Modifier.padding(16.dp).width(200.dp).align(Alignment.CenterHorizontally)
        )
        {
            Text(text = "Add Product")
        }
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
fun ProductSizeField(productSize: String, onSizeChange: (String) -> Unit) {
    TextField(
        value = productSize,
        onValueChange = onSizeChange,
        label = { Text(text = "Product Description") },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}