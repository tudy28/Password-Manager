package com.example.passwordmanager.accounts.usecase

import com.example.passwordmanager.accounts.domain.Account
import com.example.passwordmanager.accounts.repo.AccountRepository
import javax.inject.Inject

interface AddAccountUseCaseRemote {
    suspend operator fun invoke(account: Account): Boolean
}

class AddAccountUseCaseImplRemote @Inject constructor(
    val repo: AccountRepository
) : AddAccountUseCaseRemote {
    override suspend fun invoke(account: Account): Boolean {
        return repo.addAccountRemote(account)
    }
}