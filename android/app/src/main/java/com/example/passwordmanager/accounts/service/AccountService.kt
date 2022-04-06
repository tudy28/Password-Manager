package com.example.passwordmanager.accounts.service

import androidx.lifecycle.LiveData
import com.example.passwordmanager.accounts.domain.Account
import retrofit2.http.*

interface AccountService {

    @GET("api/users/{userID}/accounts")
    suspend fun getAllAccounts(@Path("userID") userID: Long):List<Account>

    @GET("api/accounts/{accountID}")
    suspend fun getAccount(@Path("accountID") accountID: Long): Account

    @POST("api/accounts")
    suspend fun addAccount(@Body account: Account)

    @DELETE("api/accounts/{accountID}")
    suspend fun deleteAccount(@Path("accountID") accountID: Long): Account

    @PUT("api/accounts/{accountID}")
    suspend fun updateAccount(@Path("accountID") accountID: Long,@Body account: Account)

}