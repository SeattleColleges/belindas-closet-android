package com.example.belindas_closet.screen

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.belindas_closet.R
import com.example.belindas_closet.Routes
import com.example.belindas_closet.data.Datasource
import com.example.belindas_closet.model.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditUserRole(navController: NavController) {
    var profileDropdownState by remember { mutableStateOf(DrawerValue.Closed) }

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        /* Back arrow that navigates back to login page */
        TopAppBar(title = { Text("Back") }, navigationIcon = {
            IconButton(onClick = {
                navController.navigate(Routes.AdminView.route)
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack, contentDescription = "Back to Home page"
                )
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
        })
    }) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomTextField(text = stringResource(R.string.edit_user_role_title))
            UserList(users = Datasource().loadUsers(), navController = navController)
        }
    }
}

@Composable
fun UserCard(user: User, navController: NavController) {
    var isEditingRole by remember { mutableStateOf(false) }
    var selectedRole by remember { mutableStateOf(user.userRole.role) }
    var isCancel by remember { mutableStateOf(false) }
    var isSave by remember { mutableStateOf(false) }
    val current = LocalContext.current

    Card(
        modifier = Modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomTextField(text = user.userFirstName + " " + user.userLastName)
            CustomTextField(text = user.userEmail)
            CustomTextField(text = user.userRole.role.lowercase().replaceFirstChar { it.uppercase() })
            Row(
                modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    isEditingRole = true
                }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                }
                if (isEditingRole) {
                    RoleSelectionDialog(selectedRole = selectedRole, onRoleSelected = {
                        selectedRole = it
                        isEditingRole = false
                        user.userRole.role = selectedRole
                        isSave = true
                    }, onDismiss = { isEditingRole = false})
                }
                if (isSave) {
                    Toast.makeText(
                        current,
                        "User's role has been updated!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}

@Composable
fun RoleSelectionDialog(
    selectedRole: String, onRoleSelected: (String) -> Unit, onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = {
        onDismiss()
    }) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .border(1.dp, Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Select Role")
                Spacer(modifier = Modifier.padding(8.dp))
                Column(
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.Top,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = selectedRole == "Admin",
                            onClick = { onRoleSelected("Admin") })
                        Text("Admin")
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = selectedRole == "Creator",
                            onClick = { onRoleSelected("Creator") })
                        Text("Creator")
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedRole == "User",
                            onClick = { onRoleSelected("User") })
                        Text("User")
                    }
                }
            }
        }
    }
}


@Composable
fun UserList(users: List<User>, navController: NavController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(users) { user ->
            UserCard(user = user, navController = navController)
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}