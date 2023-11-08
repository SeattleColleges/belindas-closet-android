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
import com.example.belindas_closet.model.ProductGender
import com.example.belindas_closet.model.ProductSizes
import com.example.belindas_closet.model.ProductSizePantsInseam
import com.example.belindas_closet.model.ProductSizePantsWaist
import com.example.belindas_closet.model.ProductSizeShoes
import com.example.belindas_closet.model.ProductType


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductPage(navController: NavHostController) {

    var selectedProductType by remember { mutableStateOf(ProductType.SHOES) }
    var productName by remember { mutableStateOf("") }
    var productDescription by remember { mutableStateOf("") }
    var productSize by remember { mutableStateOf(ProductSizes.SELECT_SIZE) } /* Default size set */
    val productImage by remember { mutableStateOf("") }
    var toastMessage by remember { mutableStateOf("") }
    var newProduct by remember { mutableStateOf<Product?>(null) }


    /* Back arrow that navigates back to login page */
    TopAppBar(
        title = { Text("Home") }, /* todo: change destination where arrow navigates to */
        navigationIcon = {
            IconButton(
                onClick = {
                    navController.navigate(Routes.Home.route) /* Navigate back to home page */
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

        // uncommented out, testing ci workflow on pr
        ProductInfoField(
            productName = productName,
            onProductChange = { newName -> productName = newName }
        )

        ProductTypeDropdown(
            selectedProductType = selectedProductType,
            onProductTypeChange = { newType -> selectedProductType = newType }
        )

        ProductSizeField(
            productSize = productSize,
            onSizeChange = { newSize -> productSize = newSize }
        )

        ProductDescriptionField(
            productDescription = productDescription,
            onDescriptionChange = { newDescription -> productDescription = newDescription }
        )


        /* TODO: finish up product button and validation logic */
        Button(
            onClick = {
                if (productName.isNotEmpty() && productSize != ProductSizes.SELECT_SIZE) {
                     newProduct = Product(
                        productType = selectedProductType,
                        productGender = ProductGender.NON_BINARY,
                        productSizeShoe = ProductSizeShoes.SELECT_SIZE,
                        productSizes = productSize,
                        productSizePantsWaist = ProductSizePantsWaist.S,
                        productSizePantsInseam = ProductSizePantsInseam.M,
                        productDescription = productDescription,
                        productImage = productImage,
                    )
                    /* TODO: Save the new product to the database or use a list to hold products */
                    // Set toast message to show success
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
        // Display the new product
        if (newProduct != null) {
            DisplayNewProduct(newProduct!!)
        }
    }
    // Display the toast message and reset it
    if (toastMessage.isNotEmpty()) {
        Toast.makeText(
            LocalContext.current,
            toastMessage,
            Toast.LENGTH_SHORT
        ).show()
        // Reset toast message
        toastMessage = ""
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
fun ProductTypeDropdown(selectedProductType: ProductType, onProductTypeChange: (ProductType) -> Unit) {
    val productTypes = ProductType.values()
    var currentProductType by remember { mutableStateOf(selectedProductType) }
    var isDropdownMenuExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isDropdownMenuExpanded = !isDropdownMenuExpanded }
    ) {
        Text(
            text = "Type: ${currentProductType.type}",
            modifier = Modifier.padding(16.dp)
        )
        DropdownMenu(
            expanded = isDropdownMenuExpanded,
            onDismissRequest = { isDropdownMenuExpanded = false }
        ) {
            productTypes.forEach { productType ->
                DropdownMenuItem(
                    text = { Text(productType.type) },
                    onClick = {
                        currentProductType = productType
                        isDropdownMenuExpanded = false
                        onProductTypeChange(productType)
                    }
                )

            }

        }

    }
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
fun ProductSizeField(productSize: ProductSizes, onSizeChange: (ProductSizes) -> Unit) {
    val sizes = ProductSizes.values() /* using enum values directly */
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

@Composable
fun DisplayNewProduct(newProduct: Product) {
    Column(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "New Product Added")
        // productName isn't in Product yet
        // Text(text = "Product Name: ${newProduct.productName}")
        Text(text = "Product Type: ${newProduct.productType.type}")
        Text(text = "Product Gender: ${newProduct.productGender.name}")
        // if product type is shoes, show shoe size
        // if product type is pants, show waist and inseam size
        // else show product size
        if (newProduct.productType == ProductType.SHOES) {
            Text(text = "Product Shoe Size: ${newProduct.productSizeShoe}")
        } else if (newProduct.productType == ProductType.PANT) {
            Text(text = "Product Pants Waist Size: ${newProduct.productSizePantsWaist}")
            Text(text = "Product Pants Inseam Size: ${newProduct.productSizePantsInseam}")
        } else {
            Text(text = "Product Size: ${newProduct.productSizes}")
        }
        Text(text = "Product Description: ${newProduct.productDescription}")
        // Add more fields as needed
    }
}