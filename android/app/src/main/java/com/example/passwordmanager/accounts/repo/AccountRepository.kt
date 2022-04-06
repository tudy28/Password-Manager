package com.example.passwordmanager.accounts.repo

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.passwordmanager.accounts.domain.Account
import com.example.passwordmanager.accounts.service.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.Exception
import java.sql.SQLException
import javax.inject.Inject

interface AccountRepository {
    suspend fun getAllAccounts(): LiveData<List<Account>>

    suspend fun getAllAccountsRemote(): List<Account>


    fun getOneAccount(accountID: Long): Account

    suspend fun getOneAccountRemote(accountID:Long): Account

    suspend fun addAccount(account: Account): Boolean

    suspend fun addAccountRemote(account: Account): Boolean


    suspend fun deleteAccount(accountID: Long): Boolean

    suspend fun deleteAccountRemote(accountID: Long): Boolean

    suspend fun updateAccount(newAccount: Account)

    suspend fun updateAccountRemote(newAccount: Account)


    suspend fun cacheAccounts(accountsFromServer:List<Account>)

    suspend fun syncAccounts()
}

class AccountRepositoryImpl @Inject constructor(
    private val accountDao : AccountDao,
    private val accountService: AccountService

) : AccountRepository {



    override suspend fun getAllAccounts(): LiveData<List<Account>> {
        try {
            return accountDao.getAccounts()
        }
        catch (e: SQLException){
            throw(Exception("A aparut o eroare la citirea conturilor!"))
        }
    }

    override suspend fun getAllAccountsRemote(): List<Account> {
        try {
            return accountService.getAllAccounts(2)
        }
        catch (e: SQLException){
            throw(Exception("A aparut o eroare la citirea conturilor!"))
        }
    }


    override fun getOneAccount(accountID: Long): Account {
        try{
        return accountDao.getAccount(accountID)
        }
        catch (e: SQLException){
            throw(Exception("A aparut o eroare la citirea unui cont!"))
        }
    }

    override suspend fun getOneAccountRemote(accountID: Long): Account{
        try{
            return accountService.getAccount(accountID)
        }
        catch (e: SQLException){
            throw(Exception("A aparut o eroare la citirea unui cont!"))
        }
    }

    override suspend fun addAccount(account: Account): Boolean {
        try {
          //  throw SQLException("BLABLA")
            accountDao.insertAccount(account)
            return true
        }
        catch (e:SQLException){
            throw(Exception("A aparut o eroare la adaugarea unui cont!"))
        }
    }

    override suspend fun addAccountRemote(account: Account): Boolean {
        try {
            accountService.addAccount(account)
            return true
        }
        catch (e:SQLException){
            throw(Exception("A aparut o eroare la adaugarea unui cont!"))
        }
    }


    override suspend fun deleteAccount(accountID: Long): Boolean {
        try {
          //  throw SQLException("BLABLA")
            accountDao.deleteAccount(accountID)
            return true
        }
        catch (e:SQLException){
            throw(Exception("A aparut o eroare la stergerea unui cont!"))
        }
    }

    override suspend fun deleteAccountRemote(accountID: Long): Boolean {
        try {
            accountService.deleteAccount(accountID)
            return true
        }
        catch (e:SQLException){
            throw(Exception("A aparut o eroare la stergerea unui cont!"))
        }
    }

    override suspend fun updateAccount(newAccount: Account) {
        try{
          //  throw SQLException("BLABLA")

            accountDao.updateAccount(newAccount)
        }
        catch (e:SQLException){
            throw(Exception("A aparut o eroare la updatarea unui cont!"))
        }
    }

    override suspend fun updateAccountRemote(newAccount: Account) {
        try{
            accountService.updateAccount(newAccount.id,newAccount)
        }
        catch (e:SQLException){
            throw(Exception("A aparut o eroare la updatarea unui cont!"))
        }
    }

    override suspend fun cacheAccounts(accountsFromServer:List<Account>) {
        try{
            accountDao.deleteAllAccounts()
            for (account:Account in accountsFromServer){
                accountDao.insertAccount(account)
            }

        }
        catch (e:SQLException){
            throw (Exception("Eroare la cacheuire"))
        }
    }

    override suspend fun syncAccounts() {
        try{
            for (account:Account in accountDao.getAllOfflineOperations()){
                accountService.addAccount(account)
                account.offline_operation=false
                accountDao.updateAccount(account)
            }
        }
        catch (e:SQLException){
            throw (Exception("Eroare la sincronizare"))
        }
    }

}