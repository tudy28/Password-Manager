package com.example.passwordmanager.accounts.usecase

import androidx.lifecycle.LiveData
import com.example.passwordmanager.accounts.domain.Account
import com.example.passwordmanager.accounts.repo.AccountRepository
import javax.inject.Inject

interface GetAccountsUseCase {
    suspend operator fun invoke(): LiveData<List<Account>>
}

class GetAccountsUseCaseImpl @Inject constructor(
    val repo: AccountRepository
) : GetAccountsUseCase {
    override suspend fun invoke(): LiveData<List<Account>> {
        return repo.getAllAccounts()
    }
}