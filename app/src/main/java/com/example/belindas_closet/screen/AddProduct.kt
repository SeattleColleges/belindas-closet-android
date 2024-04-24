package com.example.belindas_closet.screen

import android.content.Context
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
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import com.example.belindas_closet.MainActivity
import com.example.belindas_closet.R
import com.example.belindas_closet.Routes
import com.example.belindas_closet.data.network.dto.auth_dto.Role
import com.example.belindas_closet.data.network.dto.product_dto.ProductRequest
import com.example.belindas_closet.data.network.product.ProductService
import com.example.belindas_closet.model.Product
import com.example.belindas_closet.model.ProductGender
import com.example.belindas_closet.model.ProductSizePantsInseam
import com.example.belindas_closet.model.ProductSizePantsWaist
import com.example.belindas_closet.model.ProductSizeShoes
import com.example.belindas_closet.model.ProductSizes
import com.example.belindas_closet.model.ProductType
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductPage(navController: NavHostController) {
    var profileDropdownState by remember { mutableStateOf(DrawerValue.Closed) }
    var selectedProductType by remember { mutableStateOf(ProductType.SHOES) }
    var selectedProductGender by remember { mutableStateOf(ProductGender.NON_BINARY) }
    var selectedProductSizeShoe by remember { mutableStateOf(ProductSizeShoes.SELECT_SIZE) }
    var selectedProductSize by remember { mutableStateOf(ProductSizes.SELECT_SIZE) }
    var selectedProductSizePantsWaist by remember { mutableStateOf(ProductSizePantsWaist.SELECT_SIZE) }
    var selectedProductSizePantsInseam by remember { mutableStateOf(ProductSizePantsInseam.SELECT_SIZE) }
    var productDescription by remember { mutableStateOf("") }
    var productImage by remember { mutableStateOf("") }
    var newProduct by remember { mutableStateOf<Product?>(null) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

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
                    Row {
                        IconButton(
                            onClick = {
                                profileDropdownState = DrawerValue.Open
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "Profile dropdown"
                            )
                        }
                        ProfileDropdown(profileDropdownState, navController) {
                            profileDropdownState = DrawerValue.Closed
                        }
                    }
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
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            item {
                CustomTextField(
                    text = stringResource(R.string.product_add_product_title)
                )
            }

            // Product Type Dropdown
            item {
                ProductDropDownMenu(
                    selectedValue = selectedProductType.type,
                    values = ProductType.values().map { it.type },
                    label = stringResource(R.string.product_type_label),
                    onValueChange = { newType ->
                        selectedProductType = ProductType.values().find { it.type == newType }!!
                    }
                )
            }

            // Product Gender Dropdown
            item {
                ProductDropDownMenu(
                    selectedValue = selectedProductGender.name,
                    values = ProductGender.values().map { it.name },
                    label = stringResource(R.string.product_gender_label),
                    onValueChange = { newGender ->
                        selectedProductGender =
                            ProductGender.values().find { it.name == newGender }!!
                    }
                )
            }

            // Product Size Shoe Dropdown
            item {
                ProductDropDownMenu(
                    selectedValue = selectedProductSizeShoe.size,
                    values = ProductSizeShoes.values().map { it.size },
                    label = stringResource(R.string.product_size_shoe_label),
                    onValueChange = { newSizeShoe ->
                        selectedProductSizeShoe =
                            ProductSizeShoes.values().find { it.size == newSizeShoe }!!
                    }
                )
            }

            // Product Size Dropdown
            item {
                ProductDropDownMenu(
                    selectedValue = selectedProductSize,
                    values = ProductSizes.values().map { it },
                    label = stringResource(R.string.product_size_label),
                    onValueChange = { newSize ->
                        selectedProductSize = ProductSizes.values().find { it == newSize }!!
                    }
                )
            }

            // Product Size Pants Waist Dropdown
            item {
                ProductDropDownMenu(
                    selectedValue = selectedProductSizePantsWaist.size,
                    values = ProductSizePantsWaist.values().map { it.size },
                    label = stringResource(R.string.product_size_pants_waist_label),
                    onValueChange = { newSizePantsWaist ->
                        selectedProductSizePantsWaist = ProductSizePantsWaist.values()
                            .find { it.size == newSizePantsWaist }!!
                    }
                )
            }

            // Product Size Pants Inseam Dropdown
            item {
                ProductDropDownMenu(
                    selectedValue = selectedProductSizePantsInseam.size,
                    values = ProductSizePantsInseam.values().map { it.size },
                    label = stringResource(R.string.product_size_pants_inseam_label),
                    onValueChange = { newSizePantsInseam ->
                        selectedProductSizePantsInseam = ProductSizePantsInseam.values()
                            .find { it.size == newSizePantsInseam }!!
                    }
                )
            }

            // Product Description TextField
            item {
                ProductDescriptionField(
                    productDescription = productDescription,
                    onDescriptionChange = { newDescription -> productDescription = newDescription }
                )
            }

            // Product Image Upload
            item {
                ImageUploadButton(
                    onImagePicked = { uri -> productImage = uri.toString() }
                )
            }

            // Add Product Button
            item {
                Button(
                    onClick = {
                        newProduct = Product(
                            productType = selectedProductType,
                            productGender = selectedProductGender,
                            productSizeShoe = selectedProductSizeShoe,
                            productSizes = selectedProductSize,
                            productSizePantsWaist = selectedProductSizePantsWaist,
                            productSizePantsInseam = selectedProductSizePantsInseam,
                            productDescription = productDescription,
                            productImage = productImage
                        )

                        // Reset fields
                        selectedProductType = ProductType.SHOES
                        selectedProductGender = ProductGender.NON_BINARY
                        selectedProductSizeShoe = ProductSizeShoes.SELECT_SIZE
                        selectedProductSize = ProductSizes.SELECT_SIZE
                        selectedProductSizePantsWaist = ProductSizePantsWaist.SELECT_SIZE
                        selectedProductSizePantsInseam = ProductSizePantsInseam.SELECT_SIZE
                        productDescription = ""
                        productImage = ""

                        // Add the product
                        coroutineScope.launch {
                            addProduct(newProduct!!, navController, context)
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> ProductDropDownMenu(
    selectedValue: T,
    values: List<T>,
    label: String,
    onValueChange: (T) -> Unit,
) {
    var isDropdownMenuExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .padding(8.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = isDropdownMenuExpanded,
            onExpandedChange = { isDropdownMenuExpanded = it },
        ) {
            TextField(
                value = selectedValue.toString(),
                onValueChange = {},
                label = { Text(text = label) },
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = isDropdownMenuExpanded
                    )
                },
                singleLine = true,
                modifier = Modifier.menuAnchor()
            )
            DropdownMenu(
                expanded = isDropdownMenuExpanded,
                onDismissRequest = { isDropdownMenuExpanded = false }
            ) {
                values.forEach { value ->
                    DropdownMenuItem(
                        text = { Text(value.toString()) },
                        onClick = {
                            isDropdownMenuExpanded = false
                            onValueChange(value)
                        }
                    )
                }
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
            Text(text = "Product Shoe Size: ${newProduct.productSizeShoe.size}")
        } else if (newProduct.productType == ProductType.PANTS) {
            Text(text = "Product Pants Waist Size: ${newProduct.productSizePantsWaist.size}")
            Text(text = "Product Pants Inseam Size: ${newProduct.productSizePantsInseam.size}")
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
    val context = LocalContext.current

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
                Text(
                    text = stringResource(id = R.string.product_image_picker_upload_product_image),
                    fontSize = 16.sp,
                    color = Color.Black
                )
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
                Toast.makeText(context,
                    stringResource(R.string.product_image_uploaded_successfully), Toast.LENGTH_SHORT).show()
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

suspend fun addProduct(newProduct: Product, navController: NavHostController, context: Context) {
    return try {
        // Get the user role
        val userRole = MainActivity.getPref().getString("userRole", Role.USER.name)?.let {
            Role.valueOf(it) } ?: Role.USER

        // Create the product request
        val productRequest = ProductRequest(
            productType = newProduct.productType.type,
            productGender = newProduct.productGender,
            productSizeShoe = newProduct.productSizeShoe.size,
            productSizes = newProduct.productSizes,
            productSizePantsWaist = newProduct.productSizePantsWaist.size,
            productSizePantsInseam = newProduct.productSizePantsInseam.size,
            productDescription = newProduct.productDescription,
            productImage = newProduct.productImage,
        )
        ProductService.create().addProduct(productRequest)
        if (userRole != Role.ADMIN && userRole != Role.CREATOR) {
            // Unauthorized user
            Toast.makeText(
                context,
                context.getString(R.string.product_unauthorized_user),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            // Product added successfully
            Toast.makeText(
                context,
                context.getString(R.string.product_added_successfully),
                Toast.LENGTH_SHORT
            ).show()
            // Navigate to the product detail page
            MainActivity.setProductType(newProduct.productType.type)
            navController.navigate(
                Routes.ProductDetail.route)
        }
    } catch (e: Exception) {
        // Error adding product
        Toast.makeText(
            context,
            context.getString(R.string.product_error_adding_product),
            Toast.LENGTH_SHORT
        ).show()
    }
}