package com.example.passwordmanager

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.passwordmanager.accounts.viewmodel.AccountsViewModel
import com.example.passwordmanager.navigation.PasswordManagerNavigation
import com.example.passwordmanager.ui.theme.PasswordManagerTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity(){
    protected lateinit var connectionLiveData: ConnectionLiveData
    val viewModel by viewModels<AccountsViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectionLiveData = ConnectionLiveData(this)

        setContent {
            connectionLiveData.observe(this) {
                viewModel.isNetworkAvailable.value = it
                if(it == true){
                    this.viewModel.syncAccounts()
                }
            }

            viewModel.isNetworkAvailable.value = isConnected
            viewModel.load()


            val isLoading by viewModel.isLoading.collectAsState()
            PasswordManagerTheme {
                when {
                    isLoading -> LoadingUi()
                    else -> PasswordManagerApp(viewModel)
                }
            }
        }
    }


}

@Composable
fun PasswordManagerApp(viewModel: AccountsViewModel) {
    PasswordManagerNavigation(viewModel)
}


@Composable
fun LoadingUi() {
    Scaffold(
        topBar = { MainTopBar() },
        content = { LoadingIndicator() },
    )
}

@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun MainTopBar(
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        elevation = 4.dp,
        title = {
            Text(text = stringResource(id = R.string.app_name))
        },
        actions = actions
    )
}

