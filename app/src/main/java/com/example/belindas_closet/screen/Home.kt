package com.example.belindas_closet.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.belindas_closet.model.ProductType
import com.example.belindas_closet.components.DropdownNavList

//TODO Add Product Catefories to Navbar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(navController: NavController) {
    var drawerState by remember { mutableStateOf(DrawerValue.Closed) }
    TopAppBar(
        title = { Text("") },
        actions = {
            // Login button
            TextButton(onClick = { navController.navigate(Routes.Login.route) }) {
                Text(
                    "Login", style = TextStyle(
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                )
            }
            IconButton(
                onClick = {
                    navController.navigate(Routes.DonationInfo.route)
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.info_icon),
                    contentDescription = "Donation Info page",
                    modifier = Modifier.padding(10.dp)
                )
            }
            IconButton(
                onClick = {
                    drawerState = DrawerValue.Open
                }
            ) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
            }
            DropdownNavList(drawerState, navController) {
                drawerState = DrawerValue.Closed
            }
        },
    )
    Row(
        modifier = Modifier
            .size(75.dp)
            .padding(top = 20.dp, start = 10.dp),
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
    ) {
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
            Spacer(modifier = Modifier.padding(16.dp))
            // TODO Delete later. Just for testing purpose
//            TextButton(
//                onClick = {
//                    MainActivity.getPref().edit().clear().commit()
//                }
//            ) {
//                Text(text = "Reset Deleted Products (testing purposes only)")
//            }

            ProductTypeList(products = Datasource().loadProducts(), navController = navController)
        }
    }

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
fun TypeCard(productType: ProductType, navController: NavController) {
    Card(
        modifier = Modifier
            .clickable {
                MainActivity.setProductType(productType.type)
                navController.navigate(
                    Routes.ProductDetail.route
                )
            },
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = productType.image),
//                TODO: use real product type images
                contentDescription = stringResource(id = R.string.product_image_description),
                modifier = Modifier
                    .size(200.dp)
                    .padding(16.dp),
            )
            Text(
                text = productType.type,
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
fun ProductTypeList(products: List<Product>, navController: NavController) {
    val typeList = ProductType.values().sortedWith(compareBy { it.type });
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(typeList) { productType ->
            TypeCard(productType = productType, navController = navController)
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
