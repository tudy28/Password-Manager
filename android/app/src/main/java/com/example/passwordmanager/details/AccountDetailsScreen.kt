package com.example.passwordmanager.details

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Top
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.passwordmanager.accounts.domain.Account
import com.example.passwordmanager.accounts.repo.AccountRepository
import com.example.passwordmanager.accounts.repo.AccountRepositoryImpl
import com.example.passwordmanager.accounts.viewmodel.AccountsViewModel
import com.example.passwordmanager.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel


@Composable
fun AccountDetailsScreen(
    selectedAccountID: Long,
    viewModel: AccountsViewModel,
    navController: NavController,
    onEditClick: () -> Unit
) {
    val selectedAccount = viewModel.getOneAccount(selectedAccountID)
    Column {
        Header(textValue = selectedAccount.application_name)
        DeleteEditIcons(onDeleteClick = {
            viewModel.deleteAccount(selectedAccountID)
            navController.popBackStack();

        }, onEditClick = onEditClick, selectedAccount.application_name,viewModel.errorMessage)
        InfoText(
            type = "Username",
            credential = selectedAccount.username,
            iconType = Icons.Rounded.Person
        )
        InfoText(
            type = "Password",
            credential = selectedAccount.password,
            iconType = Icons.Default.Lock
        )
    }
}

@Composable
fun InfoText(
    type: String,
    credential: String,
    iconType: ImageVector
) {
    Card(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
            .height(80.dp),
        elevation = 8.dp,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically

        ) {
            Icon(
                imageVector = iconType,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 4.dp, end = 16.dp)
                    .size(width = 40.dp, height = 40.dp),
                tint = Color.White,
                contentDescription = ""
            )
            Column {
                Text(
                    text = type,
                    modifier = Modifier.offset(x = 3.dp),
                    fontSize = 14.sp
                )
                Text(
                    text = credential,
                    fontSize = 24.sp
                )
            }
        }
    }
}

@Composable
fun Header(
    textValue: String,
) {
    Card(
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth()
            .height(200.dp),
        backgroundColor = LightGray,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = textValue,
                fontWeight = FontWeight.Bold,
                fontSize = 35.sp,
                color = Black,
                fontFamily = FontFamily.Serif
            )
        }
    }
}

@Composable
fun DeleteEditIcons(
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit,
    appName: String,
    errorMessage: MutableState<String>,
) {
    val context = LocalContext.current
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Filled.Edit,
            modifier = Modifier
                .padding(bottom = 40.dp, start = 10.dp)
                .size(width = 40.dp, height = 40.dp)
                .clickable { onEditClick() },
            tint = White,
            contentDescription = "",
        )
        val openDialog = remember { mutableStateOf(false) }

        Icon(
            imageVector = Icons.Filled.Delete,
            modifier = Modifier
                .padding(bottom = 40.dp, end = 10.dp)
                .size(width = 40.dp, height = 40.dp)
                .clickable { openDialog.value = true },
            tint = White,
            contentDescription = "",
        )
        if (openDialog.value) {

            AlertDialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                title = {
                    Text(text = appName + " Account")
                },
                text = {
                    Text("Are you sure you want to delete this account?")
                },
                confirmButton = {
                    Button(
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
                        onClick = {
                            openDialog.value = false;
                            onDeleteClick()
                            if(errorMessage.value!="") {
                                Toast.makeText(
                                    context,
                                    errorMessage.value,
                                    Toast.LENGTH_LONG
                                ).show()
                            errorMessage.value="";
                            }
                        }) {
                        Text("Yes")
                    }
                },
                dismissButton = {
                    Button(
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
                        onClick = {
                            openDialog.value = false
                        }) {
                        Text("No")
                    }
                }
            )
        }
    }
}

