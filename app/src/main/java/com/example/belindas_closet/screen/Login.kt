package com.example.belindas_closet.screen

import android.util.Patterns
import android.widget.Toast
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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.belindas_closet.MainActivity
import com.example.belindas_closet.R
import com.example.belindas_closet.Routes
import com.example.belindas_closet.data.network.auth.LoginService
import com.example.belindas_closet.data.network.dto.auth_dto.LoginRequest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isEmailValid by remember { mutableStateOf(true) }
    var isPasswordValid by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()
    val current = LocalContext.current

    /* Back arrow that navigates back to login page */
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
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize(),
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
            text = stringResource(id = R.string.login_title),
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
                // Email field
                TextField(
                    value = email,
                    onValueChange = {
                        email = it
                        isEmailValid = validateEmail(it)
                    },
                    isError = !isEmailValid,
                    label = { Text(text = stringResource(id = R.string.login_email)) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp, top = 30.dp, end = 30.dp, bottom = 16.dp)
                )
                // Email display error
                if (!isEmailValid) {
                    ErrorDisplay(text = stringResource(id = R.string.login_email_error))
                }

                // Password field
                TextField(
                    value = password,
                    onValueChange = {
                        password = it
                        isPasswordValid = validatePassword(it)
                    },
                    isError = !isPasswordValid,
                    label = { Text(text = stringResource(id = R.string.login_password)) },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp, end = 30.dp, bottom = 20.dp),
                )

                // Password display error
                if (!isPasswordValid) {
                    ErrorDisplay(text = stringResource(id = R.string.login_password_error))
                }

                // Login button
                Button(
                    onClick = {
                        if (isEmailValid && isPasswordValid) {
                            coroutineScope.launch {
                                try {
                                    val loginRequest = LoginRequest(email, password)
                                    val loginResponse = LoginService.create().login(loginRequest)
                                    if (loginResponse != null) {
                                        MainActivity.getPref().edit().putString("token", loginResponse.token).apply()
                                        navController.navigate(Routes.AddProduct.route)
                                    } else {
                                        Toast.makeText(
                                            current,
                                            "Invalid email or password",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } catch (e: Exception) {
                                    // TODO: Add error handling
                                    Toast.makeText(
                                        current,
                                        "Invalid email or password",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .width(200.dp)
                        .align(Alignment.CenterHorizontally),
                ) {
                    Text(
                        text = stringResource(id = R.string.login_button_text).uppercase(),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Bold,
                        )
                    )
                }

                // Forgot password
                ClickableText(
                    text = AnnotatedString("Forgot password?"),
                    onClick = {
                        // TODO: Add forgot password functionality
                        navController.navigate(Routes.ForgotPassword.route)
                    },
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Default,
                        color = if (isSystemInDarkTheme()) Color.LightGray else Color.Black
                    ),
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                        .align(Alignment.CenterHorizontally)
                )

            }
        }
    }


    // Create a new account text
    Box(modifier = Modifier.fillMaxSize()) {
        ClickableText(
            text = AnnotatedString(text = stringResource(id = R.string.login_create_account)),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp),
            onClick = { navController.navigate(Routes.SignUp.route) },
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily.Default,
                textDecoration = TextDecoration.Underline,
                color = if (isSystemInDarkTheme()) Color.LightGray else Color.Black
            )
        )
    }
}

// Validation functions
fun validateEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email)
        .matches() && email.isNotEmpty() && email.isNotBlank() && email.length > 5 && email.length < 254 && email.contains(
        "@"
    )
}

fun validatePassword(password: String): Boolean {
    return password.isNotEmpty() && password.isNotBlank() && password.length > 5 && password.length < 30
}

// Error display function
@Composable
fun ErrorDisplay(text: String) {
    Text(
        text = text,
        color = Color.Red,
        style = TextStyle(
            fontSize = 14.sp,
            fontFamily = FontFamily.Default,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp, bottom = 8.dp)
    )
}