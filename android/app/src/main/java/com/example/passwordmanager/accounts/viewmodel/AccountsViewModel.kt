package com.example.passwordmanager.accounts.viewmodel

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.*
import com.example.passwordmanager.accounts.domain.Account
import com.example.passwordmanager.accounts.repo.AccountRepository
import com.example.passwordmanager.accounts.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Exception
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class AccountsViewModel @Inject constructor(
    private val useCaseGetAccounts: GetAccountsUseCase,
    private val useCaseGetAccountsRemote: GetAccountsUseCaseRemote,
    private val useCaseAdd: AddAccountUseCase,
    private val useCaseAddRemote: AddAccountUseCaseRemote,
    private val useCaseGetOneAccount: GetOneAccountUseCase,
    private val useCaseGetOneAccountRemote: GetOneAccountUseCaseRemote,
    private val useCaseDelete: DeleteAccountUseCase,
    private val useCaseDeleteRemote: DeleteAccountUseCaseRemote,
    private val useCaseUpdateAccount: UpdateAccountUseCase,
    private val useCaseUpdateAccountRemote: UpdateAccountUseCaseRemote,
    private val useCaseCacheAccounts: CacheAccountsUseCase,
    private val useCaseSyncAccounts: SyncAccountsUseCase,
    application:Application
) : AndroidViewModel(application) {

    var isNetworkAvailable:MutableLiveData<Boolean> =  MutableLiveData<Boolean>(true);
    var errorMessage:MutableState<String> = mutableStateOf("")

//    private val _listOfAccounts: MutableStateFlow<List<Account>> = MutableStateFlow(emptyList<Account>())
//    val listOfAccounts: StateFlow<List<Account>> = _listOfAccounts

    lateinit var listOfAccounts: LiveData<List<Account>>



    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading



    fun load() = effect {
        if (isNetworkAvailable.value == true) {
            _isLoading.value = true
            useCaseCacheAccounts(useCaseGetAccountsRemote())
            listOfAccounts = useCaseGetAccounts()
            _isLoading.value = false
        }
        else {
            _isLoading.value = true
            listOfAccounts = useCaseGetAccounts()
            _isLoading.value = false
        }
    }


    private fun effect(block: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) { block() }
    }

    fun addAccount(account: Account) {
        viewModelScope.launch {
            if (isNetworkAvailable.value == true) {

                try {
                    useCaseAddRemote(account)
                    useCaseAdd(account)
                    Log.d("Debug","Account for the application" + account.application_name+" has been added...")
                } catch (e: Exception) {
                    errorMessage.value = e.message.toString()
                    Log.d("Debug", e.toString())
                }
            }
            else{
                try {
                    account.offline_operation=true
                    useCaseAdd(account)
                    Log.d("Debug","Account for the application" + account.application_name+" has been added...")

                } catch (e: Exception) {
                    errorMessage.value = e.message.toString()
                    Log.d("Debug", e.toString())
                }
            }
        }
    }

    fun getOneAccount(accountID: Long): Account {
        var waitingAccount= Account(0, "", "", "", 0,false)
            if (isNetworkAvailable.value == true) {
                try {
                    runBlocking {
                        runBlocking { waitingAccount = async { useCaseGetOneAccountRemote(accountID) }.await() }
                    }
                    Log.d("Debug","Account with ID" + accountID+" was read...")
                    return waitingAccount
                } catch (e: Exception) {
                    Log.d("Debug", e.toString())
                    return Account(0, "", "", "", 0,false)
                }
            } else {
                try {
                    Log.d("Debug","Account with ID" + accountID+" was read...")
                    return useCaseGetOneAccount(accountID)
                } catch (e: Exception) {
                    Log.d("Debug", e.toString())
                    return Account(0, "", "", "", 0,false)
                }
            }
    }


    fun deleteAccount(accountID: Long) {
        viewModelScope.launch {
            if (isNetworkAvailable.value == true) {

                try {
                    useCaseDeleteRemote(accountID)
                    useCaseDelete(accountID)
                    Log.d("Debug","Account with ID" + accountID+" has been deleted...")
                } catch (e: Exception) {
                    errorMessage.value = e.message.toString()
                    Log.d("Debug", e.toString())
                }
            }
            else{
                try {
                    useCaseDelete(accountID)
                    Log.d("Debug","Account with ID" + accountID+" has been deleted...")
                } catch (e: Exception) {
                    errorMessage.value = e.message.toString()
                    Log.d("Debug", e.toString())
                }

            }
            //          _listOfAccounts.value=useCaseGetAccounts()
        }
    }

    fun updateAccount(newAccount: Account) {
        viewModelScope.launch {
            if (isNetworkAvailable.value == true) {
                try {
                    useCaseUpdateAccount(newAccount)
                    useCaseUpdateAccountRemote(newAccount)
                    Log.d("Debug","Account with ID" + newAccount.id+" has been modified...")
                } catch (e: Exception) {
                    errorMessage.value = e.message.toString()
                    Log.d("Debug", e.toString())
                }
            }
            else{
                try {
                    useCaseUpdateAccount(newAccount)
                    Log.d("Debug","Account with ID" + newAccount.id+" has been modified...")
                } catch (e: Exception) {
                    errorMessage.value = e.message.toString()
                    Log.d("Debug", e.toString())
                }
            }
        }
    }

    fun syncAccounts() {
        viewModelScope.launch {
            useCaseSyncAccounts()
            Log.d("Debug","Network is back...Sync operation done...")
        }
    }

//    fun isOnline(context: Context): Boolean {
//        val connectivityManager =
//            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        if (connectivityManager != null) {
//            val capabilities =
//                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
//            if (capabilities != null) {
//                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
//                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
//                    return true
//                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
//                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
//                    return true
//                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
//                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
//                    return true
//                }
//            }
//        }
//        return false
//    }


}
