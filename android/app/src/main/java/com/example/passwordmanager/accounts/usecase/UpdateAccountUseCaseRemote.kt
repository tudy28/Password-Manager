package com.example.passwordmanager.accounts.usecase

import com.example.passwordmanager.accounts.domain.Account
import com.example.passwordmanager.accounts.repo.AccountRepository
import javax.inject.Inject

interface UpdateAccountUseCaseRemote {
    suspend operator fun invoke(newAccount: Account)
}

class UpdateAccountUseCaseImplRemote @Inject constructor(
    val repo: AccountRepository
) : UpdateAccountUseCaseRemote {
    override suspend fun invoke(newAccount: Account) {
        return repo.updateAccountRemote(newAccount)
    }
}