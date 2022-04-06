package com.example.passwordmanager.add_account

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.passwordmanager.accounts.domain.Account
import com.example.passwordmanager.accounts.viewmodel.AccountsViewModel
import com.example.passwordmanager.navigation.Screen

@Composable
fun AddAccountScreen(
    viewModel: AccountsViewModel,
    navController: NavController
) {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var appName by remember { mutableStateOf("") }


    Column {
        Header()
        //textInput(iconType = Icons.Filled.Person,type = "Username",textState = username)
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = username,
                onValueChange = { newValue-> username =newValue },
                Modifier
                    .padding(top = 30.dp)
                    .width(300.dp),
                placeholder = { Text("Username") },
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
                onValueChange = { newValue->password=newValue  },
                Modifier
                    .padding(top = 30.dp)
                    .width(300.dp),
                placeholder = { Text("Password") },
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
                onValueChange = { newValue->appName=newValue },
                Modifier
                    .padding(top = 30.dp)
                    .width(300.dp),
                placeholder = { Text("App Name") },
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
        val context = LocalContext.current
        SubmitButton(onSubmitClick = {
            viewModel.addAccount(Account(0,appName,username,password,2,false))
            if(viewModel.errorMessage.value!="") {
                Toast.makeText(
                    context,
                    viewModel.errorMessage.value,
                    Toast.LENGTH_LONG
                ).show()
                viewModel.errorMessage.value="";
            }
            navController.popBackStack()
        })
    }
}

@Composable
fun textInput(
    iconType: ImageVector,
    type: String,
    textState: String,
    ){
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = textState,
            onValueChange = {  },
            Modifier
                .padding(top = 30.dp)
                .width(300.dp),
            placeholder = { Text(type) },
            leadingIcon = {
                Icon(
                    imageVector = iconType,
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
}

@Composable
fun Header(
) {Card(
    modifier = Modifier
        .padding(bottom = 60.dp)
        .fillMaxWidth()
        .height(200.dp),
    backgroundColor = Color.LightGray,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Filled.Lock,
            modifier = Modifier
                .size(width = 150.dp, height = 150.dp),
            tint = Color.Black,
            contentDescription = ""
        )
    }
}
}

@Composable
fun SubmitButton(
    onSubmitClick: () -> Unit,
){
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedButton(
            onClick = onSubmitClick,
            enabled = true,
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
            border = BorderStroke(0.dp, Color.Transparent),
            modifier = Modifier
                .size(180.dp,100.dp)
                .padding(top = 60.dp)
        )
        {
            Text(text = "Submit", color = Color.Gray)
        }
    }
}