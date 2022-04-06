package com.example.passwordmanager.accounts.usecase

import androidx.lifecycle.LiveData
import com.example.passwordmanager.accounts.domain.Account
import com.example.passwordmanager.accounts.repo.AccountRepository
import javax.inject.Inject

interface GetAccountsUseCaseRemote {
    suspend operator fun invoke(): List<Account>
}

class GetAccountsUseCaseRemoteImpl @Inject constructor(
    val repo: AccountRepository
) : GetAccountsUseCaseRemote {
    override suspend fun invoke(): List<Account> {
        return repo.getAllAccountsRemote()
    }
}