package com.example.belindas_closet.screen

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.belindas_closet.MainActivity
import com.example.belindas_closet.R
import com.example.belindas_closet.Routes
import com.example.belindas_closet.data.Datasource
import com.example.belindas_closet.model.Product


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(navController: NavController) {

    Row(
        modifier = Modifier
            .size(125.dp)
            .padding(top = 10.dp, start = 10.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start
    ) {
        NSCLogo()
    }
    Row(
        modifier = Modifier
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(50.dp))
            Image(
                painter = painterResource(id = R.drawable.packaging),
                contentDescription = stringResource(id = R.string.home_logo_description),
                modifier = Modifier
                    .size(100.dp)
                    .padding(8.dp, bottom = 16.dp)
            )
            CustomTextField(
                text = stringResource(R.string.home_welcome),
                fontSize = 30.sp
            )
            Spacer(modifier = Modifier.padding(8.dp))
            NavigateButtons(
                navController = navController,
                text = stringResource(R.string.home_login)
            )
            Spacer(modifier = Modifier.padding(16.dp))

            // TODO Delete later. Just for testing purpose
            TextButton(
                onClick = {
                    MainActivity.getPref().edit().clear().commit()
                }
            ) {
                Text(text = "Reset Deleted Products (testing purposes only)")
            }

            ProductList(products = Datasource().loadProducts(), navController = navController)
        }
    }

//    Scaffold(
//
//        modifier = Modifier
//            .fillMaxSize(),
//        topBar = {
//            /* Don't need back button on this page? */
//            /* Back arrow that navigates back to different page? */
//            TopAppBar(
//                title = { Text("Belinda's Home Page") },
//                navigationIcon = {
//                    IconButton(
//                        onClick = {
//                            navController.navigate(Routes.Home.route)
//                        }
//                    ) {
//
//                        Icon(
//                            imageVector = Icons.Default.ArrowBack,
//                            contentDescription = "Back to Product page"
//                        )
//                    }
//                },
//                actions = {
//                    Row(
//                        horizontalArrangement = Arrangement.spacedBy(8.dp), // Adjust spacing as needed
//                        modifier = Modifier.fillMaxWidth(),
//                    ) {
//                        IconButton(
//                            onClick = { /*TO DO*/ }
//                        ) {
//                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
//                        }
//                    }
//                }
//            )
//        },
//    ) { innerPadding ->
//        val modifier = Modifier.padding(innerPadding)
//        Column(
//            modifier = modifier
//                .fillMaxSize(),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//        }
//    }
}


@Composable
fun CustomTextField(text: String, fontSize: TextUnit = 20.sp) {
    Text(
        text = text,
        style = TextStyle(
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Default,
        ),
        modifier = Modifier
            .wrapContentSize()
    )
}

@Composable
fun ProductCard(product: Product, navController: NavController) {
    Card(
        modifier = Modifier
            .clickable {
                navController.navigate(Routes.ProductDetail.route)
            },
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
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
            Text(
                text = product.productType.type,
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Default,
                ),
                modifier = Modifier
                    .wrapContentSize()
            )
        }
    }
}

@Composable
fun ProductList(products: List<Product>, navController: NavController) {
    val hidden = MainActivity.getPref().getStringSet("hidden", setOf(""))
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(products.filter { !hidden!!.contains(it.productType.name) }) { product ->
            ProductCard(product = product, navController = navController)
            Spacer(modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun NavigateButtons(navController: NavController, text: String) {
    Button(
        onClick = {
            navController.navigate(Routes.Login.route)
        },
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default,
            )
        )
    }
}

@Composable
fun NSCLogo() {
    Image(
        painter = painterResource(id = R.drawable.nsc_v_logo),
        contentDescription = stringResource(id = R.string.home_nsc_logo_description)
    ) 
}
