package com.example.passwordmanager.accounts

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.example.passwordmanager.accounts.domain.Account
import com.example.passwordmanager.accounts.viewmodel.AccountsViewModel
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AccountsScreen(
    viewModel: AccountsViewModel,
    onAccountClick: (Long) -> Unit,
    onAddAccountClick: () -> Unit

) {

    //Starts observing this LiveData and represents its values via State.
    val listOfAccounts = viewModel.listOfAccounts.observeAsState(listOf()).value
    Column {
        Header(onAddAccountClick = onAddAccountClick)
        LazyColumn {
            items(listOfAccounts) { item ->
                SingleAccountItem(
                    account = item,
                    onAccountClick = onAccountClick
                )
            }
        }
     }

}


@OptIn(ExperimentalCoilApi::class)
@Composable
fun SingleAccountItem(
    account: Account,
    onAccountClick: (Long) -> Unit,

    ) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                onAccountClick(account.id)
            },
        elevation = 8.dp,
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = account.application_name,
                fontSize = 24.sp
            )
        }
    }
}

@Composable
fun AddAccountButton(
    onAddAccountClick: () -> Unit,
) {
    OutlinedButton(
        onClick = onAddAccountClick,
        modifier = Modifier
            .size(55.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
        shape = CircleShape,
        contentPadding = PaddingValues(0.dp),  //avoid the little icon
    ) {
        Icon(Icons.Default.Add, contentDescription = "", tint = Color.White)
    }
}


@Composable
fun Header(
    onAddAccountClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(bottom = 60.dp)
            .fillMaxWidth()
            .height(150.dp),
        backgroundColor = Color.LightGray,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            val textState = remember { mutableStateOf(TextFieldValue()) }
            TextField(
                value = textState.value,
                onValueChange = { textState.value = it },
                Modifier.padding(end = 10.dp),
                placeholder = { Text("Search...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = ""
                    )
                },
                shape = RoundedCornerShape(25.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    backgroundColor =  Color.Black,
                    cursorColor = Color.Gray,
                    textColor = Color.Gray,
                ),
            )
            AddAccountButton(onAddAccountClick)
        }
    }
}
