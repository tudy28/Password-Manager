package com.example.passwordmanager.accounts.usecase

import com.example.passwordmanager.accounts.domain.Account
import com.example.passwordmanager.accounts.repo.AccountRepository
import javax.inject.Inject

interface CacheAccountsUseCase {
    suspend operator fun invoke(accountsFromServer: List<Account>)
}

class CacheAccountsUseCaseImpl @Inject constructor(
    val repo: AccountRepository
) : CacheAccountsUseCase {
    override suspend fun invoke(accountsFromServer: List<Account>){
        repo.cacheAccounts(accountsFromServer)
    }
}