package com.example.passwordmanager.accounts.repo

import androidx.lifecycle.LiveData;
import androidx.room.*

import com.example.passwordmanager.accounts.domain.Account;


@Dao
interface AccountDao{

    // LiveData is a data holder class that can be observed within a given lifecycle.
    // Always holds/caches latest version of data. Notifies its active observers when the
    // data has changed. Since we are getting all the contents of the database,
    // we are notified whenever any of the database contents have changed.
    @Query("SELECT * from accounts_table")
    fun getAccounts(): LiveData<List<Account>>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAccount(account: Account)


    @Query("DELETE FROM accounts_table WHERE account_id=:accountID")
    fun deleteAccount(accountID: Long);

    @Query("DELETE FROM accounts_table")
    fun deleteAllAccounts()


    @Query("SELECT * FROM accounts_table WHERE account_id=:accountID")
    fun getAccount(accountID: Long): Account;


    @Update
    fun updateAccount(newAccount: Account)

    @Query("SELECT * FROM accounts_table WHERE offline_operation=1")
    fun getAllOfflineOperations(): List<Account>
}

