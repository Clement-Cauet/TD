package com.unilasalle.td.ui.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.unilasalle.td.services.database.DatabaseProvider
import com.unilasalle.td.services.database.entities.Users
import kotlinx.coroutines.launch

/**
 * User list component
 *
 * This component displays a list of users
 */
@Composable
fun UserListComponent() {
    val context = LocalContext.current
    val databaseProvider = context as DatabaseProvider
    val usersController = databaseProvider.database.usersController()
    val coroutineScope = rememberCoroutineScope()

    var users by remember { mutableStateOf(listOf<Users>()) }
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        users = usersController.getAll()
    }

    fun deleteUser(user: Users) {
        coroutineScope.launch {
            usersController.delete(user)
            users = usersController.getAll()
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = { showDialog = true }) {
            Text("Add User")
        }

        LazyColumn(modifier = Modifier.fillMaxSize().padding(top = 16.dp)) {
            items(users) { user ->
                UserItem(user, onDelete = { deleteUser(user) })
            }
        }
    }

    if (showDialog) {
        AddUserDialog(context = context, onDismiss = { showDialog = false }) { newUser ->
            coroutineScope.launch {
                usersController.insert(newUser)
                users = usersController.getAll()
            }
            showDialog = false
        }
    }
}

/**
 * User item
 *
 * This component displays a single user
 *
 * @param users The user
 * @param onDelete The function to call when the user is deleted
 */
@Composable
fun UserItem(users: Users, onDelete: () -> Unit) {
    val backgroundColor = if (users.gender == "Male") Color.Cyan else Color.Magenta

    Row(
        modifier = Modifier.fillMaxWidth().background(backgroundColor).padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Row { Text("${users.firstName} ${users.lastName}") }
            Text("${users.phoneNumber}")
        }
        Column {
            Text("${users.age}")
        }
        Column {
            DeleteButtonComponent { onDelete() }
        }
    }
}

/**
 * Add user dialog
 *
 * This component displays a dialog to add a user
 *
 * @param context The context
 * @param onDismiss The function to call when the dialog is dismissed
 * @param onAdd The function to call when the user is added
 */
@Composable
fun AddUserDialog(context: Context, onDismiss: () -> Unit, onAdd: (Users) -> Unit) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    val genderOptions = listOf("Male", "Female")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add User") },
        text = {
            Column {
                TextField(value = firstName, onValueChange = { firstName = it }, label = { Text("First Name") })
                TextField(value = lastName, onValueChange = { lastName = it }, label = { Text("Last Name") })
                TextField(value = phoneNumber, onValueChange = { phoneNumber = it }, label = { Text("Phone Number") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number) )

                Text("Gender")
                genderOptions.forEach { option ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (gender == option),
                            onClick = { gender = option }
                        )
                        Text(text = option)
                    }
                }

                TextField(value = age, onValueChange = { age = it }, label = { Text("Age") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number) )
            }
        },
        confirmButton = {
            Button(onClick = {
                if (firstName.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty() || gender.isEmpty() || age.isEmpty()) {
                    Toast.makeText(context, "All fields must be filled", Toast.LENGTH_SHORT).show()
                } else {
                    val newUsers = Users(firstName = firstName, lastName = lastName, phoneNumber = phoneNumber.toInt(), gender = gender, age = age.toInt())
                    onAdd(newUsers)
                }
            }) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}