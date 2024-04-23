package com.example.belindas_closet.screen

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.belindas_closet.MainActivity
import com.example.belindas_closet.R
import com.example.belindas_closet.Routes
import com.example.belindas_closet.data.network.dto.auth_dto.Role

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DonationInfoPage(navController: NavController) {
    var profileDropdownState by remember { mutableStateOf(DrawerValue.Closed) }

    /* Back arrow that navigates back to Home page */
    TopAppBar(
        title = { Text("Home") },
        navigationIcon = {
            IconButton(
                onClick = {
                    navController.navigate(Routes.Home.route)
                }
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            val userRole = MainActivity.getPref().getString("userRole", Role.USER.name)?.let {
                Role.valueOf(it)
            } ?: Role.USER
            if (userRole == Role.ADMIN || userRole == Role.CREATOR) {
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
            }
            IconButton(
                onClick = {
                }
            ) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
            }
        }
    )


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(75.dp))
            Text(
                text = stringResource(id = R.string.donation_info),
                style = TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Light,
                    color = if (isSystemInDarkTheme()) Color.White else Color.Black
                ),
                modifier = Modifier.wrapContentSize()
            )
            Spacer(modifier = Modifier.height(25.dp))
        }

    }
}
