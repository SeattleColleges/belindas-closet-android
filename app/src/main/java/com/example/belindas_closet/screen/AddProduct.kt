package com.example.belindas_closet.screen


import android.graphics.ImageDecoder
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.example.belindas_closet.Routes
import com.example.belindas_closet.model.Product
import androidx.compose.ui.platform.LocalContext
import com.example.belindas_closet.model.ProductGender
import com.example.belindas_closet.model.ProductSizes
import com.example.belindas_closet.model.ProductSizePantsInseam
import com.example.belindas_closet.model.ProductSizePantsWaist
import com.example.belindas_closet.model.ProductSizeShoes
import com.example.belindas_closet.model.ProductType


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductPage(navController: NavHostController) {

    var selectedProductType by remember { mutableStateOf(ProductType.SHIRTS) }
    var productDescription by remember { mutableStateOf("") }
    var productSize by remember { mutableStateOf(ProductSizes.SELECT_SIZE) } /* Default size set */
    val productImage by remember { mutableStateOf("") }
    
    /* // todo: button for inserting an image, need to change productImage type to BitImage everywhere it exists
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        uri: Uri? ->
        uri?.let {
            imageUri = it
            val source = ImageDecoder.createSource(context.contentResolver, it)
            val bitmap = ImageDecoder.decodeBitmap(source)
            productImage = bitmap.asImageBitmap()
        }
    }
    */

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
                if (productName.isNotEmpty()
                    && productDescription.isNotEmpty()
                    && productSize != ProductSizes.SELECT_SIZE) {
                    val newProduct = Product(
                        productType = ProductType.SHOES,
                        productGender = ProductGender.NON_BINARY,
                        productSizeShoe = ProductSizeShoes.SELECT_SIZE,
                        productSizes = productSize,
                        productSizePantsWaist = ProductSizePantsWaist.S,
                        productSizePantsInseam = ProductSizePantsInseam.M,
                        productDescription = productDescription,
                        productImage = productImage,
                    )
                    /* TODO: save new product to db or use a list to hold products (ex: List<Product>) */
                } else {
                    /* TODO: show error message for empty fields */
                }
            },
            modifier = Modifier
                .padding(16.dp)
                .width(200.dp)
                .align(Alignment.CenterHorizontally)
        )
        {
            Text(text = "Add Product")
        }
    }

}

/*
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
*/

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
            text = "Type: ${currentProductType.name}",
            modifier = Modifier.padding(16.dp)
        )
        DropdownMenu(
            expanded = isDropdownMenuExpanded,
            onDismissRequest = { isDropdownMenuExpanded = false }
        ) {
            productTypes.forEach { productType ->
                DropdownMenuItem(
                    text = { Text(productType.name) },
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