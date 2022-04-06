package com.example.passwordmanager.edit_account

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.passwordmanager.accounts.domain.Account
import com.example.passwordmanager.accounts.viewmodel.AccountsViewModel
import com.example.passwordmanager.add_account.SubmitButton
import com.example.passwordmanager.details.DeleteEditIcons
import com.example.passwordmanager.details.Header
import com.example.passwordmanager.details.InfoText
import com.example.passwordmanager.navigation.Screen

@Composable
fun EditAccountScreen(
    selectedAccountID: Long,
    viewModel: AccountsViewModel,
    navController: NavController,
) {
    val selectedAccount = viewModel.getOneAccount(selectedAccountID)
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var appName by remember { mutableStateOf("") }
    val context = LocalContext.current
    Column {
        Header(textValue = selectedAccount.application_name)
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = username,
                onValueChange = { newValue -> username = newValue },
                Modifier
                    .padding(top = 30.dp)
                    .width(300.dp),
                placeholder = { Text(selectedAccount.username) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = ""
                    )
                },
                shape = RoundedCornerShape(25.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    backgroundColor = Color.Black,
                    cursorColor = Color.Gray,
                    textColor = Color.Gray,
                ),
            )
        }
        //textInput(iconType = Icons.Filled.Lock,type = "Password",textState = password)
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = password,
                onValueChange = { newValue -> password = newValue },
                Modifier
                    .padding(top = 30.dp)
                    .width(300.dp),
                placeholder = { Text(selectedAccount.password) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = ""
                    )
                },
                shape = RoundedCornerShape(25.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    backgroundColor = Color.Black,
                    cursorColor = Color.Gray,
                    textColor = Color.Gray,
                ),
            )
        }
        //textInput(iconType = Icons.Filled.Info,type = "App Name",textState = appName)
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = appName,
                onValueChange = { newValue -> appName = newValue },
                Modifier
                    .padding(top = 30.dp)
                    .width(300.dp),
                placeholder = { Text(selectedAccount.application_name) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = ""
                    )
                },
                shape = RoundedCornerShape(25.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    backgroundColor = Color.Black,
                    cursorColor = Color.Gray,
                    textColor = Color.Gray,
                ),
            )
        }
        if (appName == "")
            appName = selectedAccount.application_name
        if (username == "")
            username = selectedAccount.username
        if (password == "")
            password = selectedAccount.password
        SubmitButton(onSubmitClick = {
            viewModel.updateAccount(
                Account(
                    selectedAccount.id,
                    appName,
                    username,
                    password,
                    selectedAccount.user_id,
                    false
                )
            )
            if(viewModel.errorMessage.value!="") {
                Toast.makeText(
                    context,
                    viewModel.errorMessage.value,
                    Toast.LENGTH_LONG
                ).show()
                viewModel.errorMessage.value="";
            }
            navController.popBackStack();
            navController.popBackStack();

        })
    }
}