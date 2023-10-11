package com.example.belindas_closet.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.belindas_closet.R
import com.example.belindas_closet.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpPage( navController: NavHostController) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var isEmailValid by remember { mutableStateOf(true) }

    /* Back arrow that navigates back to login page */
    TopAppBar(
        title = { Text("Login") },
        navigationIcon = {
            IconButton(
                onClick = {
                    navController.navigate(Routes.Login.route)
                }
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }
    )
    Column (
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.packaging),
            contentDescription = stringResource(id = R.string.signup_logo_description),
            modifier = Modifier
                .size(50.dp)
        )
        Text(
            text = stringResource(id = R.string.signup_title),
            style = TextStyle(
                fontSize = 30.sp,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Light,
                color = if (isSystemInDarkTheme()) Color.White else Color.Black
            ),
            modifier = Modifier
                .wrapContentSize()
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = MaterialTheme.shapes.small
                    ),
            ) {
                // First name field
                TextField(
                    value = firstName,
                    onValueChange = {
                        firstName = it
                    },
                    label = { Text(text = stringResource(id = R.string.signup_first_name)) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp, top = 30.dp, end = 30.dp, bottom = 16.dp)
                )

                // Last name field
                TextField(
                    value = lastName,
                    onValueChange = {
                        lastName = it
                    },
                    label = { Text(text = stringResource(id = R.string.signup_last_name)) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp, end = 30.dp, bottom = 16.dp)
                )

                // Email field
                TextField(
                    value = email,
                    onValueChange = {
                        email = it
                        isEmailValid = validateEmail(it)
                    },
                    isError = !isEmailValid,
                    label = { Text(text = stringResource(id = R.string.signup_email)) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp, end = 30.dp, bottom = 20.dp)
                )
                // Email display error
                if (!isEmailValid) {
                    ErrorDisplay(text = stringResource(id = R.string.signup_email_error))
                }

                // SignUp button
                Button(
                    onClick = {
                        try {
                            // TODO: Add sign up functionality and ensure type safety
                        } catch (e: Exception) {
                            // TODO: Add error handling
                        }
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .width(225.dp)
                        .align(Alignment.CenterHorizontally),
                ) {
                    Text(
                        text = stringResource(id = R.string.signup_button_text).uppercase(),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Bold,
                        )
                    )
                }

            }
        }
    }
    }
