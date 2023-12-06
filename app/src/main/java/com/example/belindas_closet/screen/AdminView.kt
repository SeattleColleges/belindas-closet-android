package com.example.belindas_closet.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.belindas_closet.MainActivity
import com.example.belindas_closet.R
import com.example.belindas_closet.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminView(navController: NavHostController) {
    val current = LocalContext.current
    Row(
        modifier = Modifier
            .height(75.dp)
            .padding(10.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        NSCLogo()
        Icon(Icons.Filled.ExitToApp, contentDescription="Logout",
            modifier = Modifier.clickable {
                MainActivity.getPref().edit().remove("token").commit()
                MainActivity.getPref().edit().remove("userRole").commit()
                navController.navigate(Routes.Home.route)
                Toast.makeText(
                    current,
                    "Logged Out",
                    Toast.LENGTH_SHORT
                ).show()
            })
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
            Spacer(modifier = Modifier.padding(16.dp))

            // Add Product button
            Button(
                onClick = {
                    navController.navigate(Routes.AddProduct.route)
                },
                modifier = Modifier
                    .padding(4.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Add Product")
            }
            Button(
                onClick = {
                    navController.navigate(Routes.Home.route)
                },
                modifier = Modifier
                    .padding(4.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(text = "All Products")
            }
            Button(
                onClick = {
                    navController.navigate(Routes.EditUserRole.route)
                },
                modifier = Modifier
                    .padding(4.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Edit User Role")
            }
        }
    }

}