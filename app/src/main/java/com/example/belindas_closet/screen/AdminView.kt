package com.example.belindas_closet.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.belindas_closet.R
import com.example.belindas_closet.Routes

@Composable
fun AdminView(navController: NavController) {
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

            // Add Product button (Temporary),
            // todo: will later be moved and protected for only admin access
            Button(
                onClick = {
                    /*TODO add navigation logic to the protected page only allowing Admin access*/
                    navController.navigate(Routes.AddProduct.route)
                },
                modifier = Modifier
                    .padding(4.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Add Product")
            }
        }
    }

}