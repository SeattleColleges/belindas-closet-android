package com.example.belindas_closet.screen
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
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
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.belindas_closet.R
import com.example.belindas_closet.model.Product
import com.example.belindas_closet.model.ProductGender
import com.example.belindas_closet.model.ProductSizePantsInseam
import com.example.belindas_closet.model.ProductSizePantsWaist
import com.example.belindas_closet.model.ProductSizeShoes
import com.example.belindas_closet.model.ProductSizes
import com.example.belindas_closet.model.ProductType


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductPage(navController: NavHostController) {

    var selectedProductType by remember { mutableStateOf(ProductType.SHOES) }
    var productDescription by remember { mutableStateOf("") }
    var productSize by remember { mutableStateOf(ProductSizes.SELECT_SIZE) } /* Default size set */
    var productImage by remember { mutableStateOf("") }
    var toastMessage by remember { mutableStateOf("") }
    var newProduct by remember { mutableStateOf<Product?>(null) }    
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
    
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
      /* Back arrow that navigates back to login page */
          TopAppBar(
              title = { Text("Back") },
              navigationIcon = {
                  IconButton(
                      onClick = {
                          navController.popBackStack()
                      }
                  ) {
                      Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                  }
              },
                  actions = {
                      IconButton(
                          onClick = {
                              // Handle menu icon click
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                    }
                }
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // uncommented out, testing ci workflow on pr
            item {
                ProductTypeDropdown(
                    selectedProductType = selectedProductType,
                    onProductTypeChange = { newType -> selectedProductType = newType }
                )
            }

            item {
                ProductSizeField(
                    productSize = productSize,
                    onSizeChange = { newSize -> productSize = newSize }
                )
            }

            item {
                ProductDescriptionField(
                    productDescription = productDescription,
                    onDescriptionChange = { newDescription -> productDescription = newDescription }
                )
            }

            item {
                ImageUploadButton(
                    onImagePicked = { uri -> productImage = uri.toString() }
                )
            }

            item {
                /* TODO: finish up product button and validation logic */
                Button(
                    onClick = {
                        if (productSize != ProductSizes.SELECT_SIZE) {
                            newProduct = Product(
                                productType = selectedProductType,
                                productGender = ProductGender.NON_BINARY,
                                productSizeShoe = ProductSizeShoes.SELECT_SIZE,
                                productSizes = productSize,
                                productSizePantsWaist = ProductSizePantsWaist.S,
                                productSizePantsInseam = ProductSizePantsInseam.M,
                                productDescription = productDescription,
                                productImage = productImage
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
                ) {
                    Text(text = "Add Product")
                }
            }
            item {
                // Display the new product
                if (newProduct != null) {
                    DisplayNewProduct(newProduct!!)
                }
            }
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
        } else if (newProduct.productType == ProductType.PANTS) {
            Text(text = "Product Pants Waist Size: ${newProduct.productSizePantsWaist}")
            Text(text = "Product Pants Inseam Size: ${newProduct.productSizePantsInseam}")
        } else {
            Text(text = "Product Size: ${newProduct.productSizes}")
        }
        Text(text = "Product Description: ${newProduct.productDescription}")
        // Add more fields as needed
    }
}

// Image Picker
@Composable
fun ImageUploadButton(onImagePicked: (Uri?) -> Unit) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        onImagePicked(imageUri)
    }

    val boxSize = Modifier
        .fillMaxWidth()
        .padding(12.dp)
        .height(200.dp)

    Box(
        modifier = boxSize
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .clickable {
                launcher.launch("image/*")
            }
            .clip(RoundedCornerShape(4.dp))
            .drawDottedBorder(2.dp, Color.DarkGray, 12.dp),
        contentAlignment = Alignment.Center
    ) {
        if (imageUri == null) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(4.dp))
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.product_image_picker_upload_icon),
                    tint = Color.Gray,
                    modifier = Modifier.size(36.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = stringResource(id = R.string.product_image_picker_upload_product_image), fontSize = 16.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(4.dp))
            }
        } else {
            imageUri?.let { uri ->
                Image(
                    painter = rememberAsyncImagePainter(model = uri),
                    contentDescription = stringResource(id = R.string.product_image_picker_product_image),
                    modifier = boxSize,
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}
fun Modifier.drawDottedBorder(
    strokeWidth: Dp,
    color: Color,
    dashWidth: Dp
): Modifier = composed {
    this.then(
        drawWithContent {
            drawContent()
            val pathEffect =
                PathEffect.dashPathEffect(floatArrayOf(dashWidth.toPx(), dashWidth.toPx()), 0f)
            val halfStrokeWidth = strokeWidth.toPx() / 3
            translate(top = halfStrokeWidth, left = halfStrokeWidth) {
                drawRoundRect(
                    color = color,
                    size = size.copy(
                        width = size.width - strokeWidth.toPx(),
                        height = size.height - strokeWidth.toPx()
                    ),
                    style = Stroke(
                        width = strokeWidth.toPx(),
                        pathEffect = pathEffect as PathEffect?
                    )
                )
            }
        }
    )
}
