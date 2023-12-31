package com.example.belindas_closet.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.belindas_closet.R
import com.example.belindas_closet.Routes
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ResetPasswordPage(navController: NavHostController) {
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isPasswordValid by remember { mutableStateOf(true) }
    var doPasswordsMatch by remember { mutableStateOf(true) }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Login") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigate(Routes.Login.route) {
                                popUpTo(Routes.Login.route) {
                                    inclusive = true
                                }
                            }
                        }
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = stringResource(
                            id = R.string.reset_new_password_to_login_back_button_description
                        ))
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
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
//                .fillMaxSize() // comment out this line to avoid keyboard overlap
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.packaging),
                contentDescription = stringResource(id = R.string.login_logo_description),
                modifier = Modifier
                    .size(50.dp)
            )
            Text(
                text = stringResource(id = R.string.reset_password_title),
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
                    // Password field and toggle button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Password field
                        TextField(
                            value = password,
                            onValueChange = {
                                password = it
                                isPasswordValid = validatePassword(it)
                                doPasswordsMatch = validateConfirmPassword(password, confirmPassword)
                            },
                            isError = !isPasswordValid,
                            label = { Text(text = stringResource(id = R.string.reset_new_password_label)) },
                            singleLine = true,
                            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 30.dp, end = 8.dp, bottom = 16.dp),
                        )

                        // Toggle button to show/hide password
                        IconButton(
                            onClick = {
                                isPasswordVisible = !isPasswordVisible
                            }
                        ) {
                            Icon(
                                painter = if (isPasswordVisible) painterResource(R.drawable.baseline_visibility_24) else painterResource(R.drawable.baseline_visibility_off_24),
                                contentDescription = if (isPasswordVisible) "Hide Password" else "Show Password",
                                modifier = Modifier
                                    .padding(top = 15.dp),
                            )
                        }
                    }

                    // Password display error
                    if (!isPasswordValid) {
                        ErrorDisplay(text = stringResource(id = R.string.signup_password_error))
                    }

                    // Confirm password field and toggle button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Confirm password field
                        TextField(
                            value = confirmPassword,
                            onValueChange = {
                                confirmPassword = it
                                doPasswordsMatch = validateConfirmPassword(password, it)
                            },
                            isError = !doPasswordsMatch,
                            label = { Text(text = stringResource(id = R.string.reset_confirm_new_password_label)) },
                            singleLine = true,
                            visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 30.dp, end = 8.dp, bottom = 16.dp),
                        )

                        // Toggle button to show/hide confirm password
                        IconButton(
                            onClick = {
                                isConfirmPasswordVisible = !isConfirmPasswordVisible
                            }
                        ) {
                            Icon(
                                painter = if (isConfirmPasswordVisible) painterResource(R.drawable.baseline_visibility_24) else painterResource(R.drawable.baseline_visibility_off_24),
                                contentDescription = if (isConfirmPasswordVisible) "Hide Confirm Password" else "Show Confirm Password",
                                modifier = Modifier
                                    .padding(top = 15.dp),
                            )
                        }
                    }

                    // Confirm password display error
                    if (!doPasswordsMatch) {
                        ErrorDisplay(text = stringResource(id = R.string.signup_confirm_password_error))
                    }


                    // SignUp button
                    Button(
                        onClick = {
                            keyboardController?.hide()
                            // Check if all fields are valid
                            if (isPasswordValid && doPasswordsMatch) {
                                // Sign up
                                coroutineScope.launch {
                                    // TODO : Call reset password function
                                }
                            }
                        },
                        modifier = Modifier
                            .padding(16.dp)
                            .width(225.dp)
                            .align(Alignment.CenterHorizontally),
                    ) {
                        Text(
                            text = stringResource(id = R.string.reset_password_submit_button).uppercase(),
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
}

// TODO: Create a function to update the password in the database