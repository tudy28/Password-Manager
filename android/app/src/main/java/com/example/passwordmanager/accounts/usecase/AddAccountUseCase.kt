package com.example.passwordmanager.accounts.usecase

import com.example.passwordmanager.accounts.domain.Account
import com.example.passwordmanager.accounts.repo.AccountRepository
import javax.inject.Inject

interface AddAccountUseCase {
    suspend operator fun invoke(account: Account): Boolean
}

class AddAccountUseCaseImpl @Inject constructor(
    val repo: AccountRepository
) : AddAccountUseCase {
    override suspend fun invoke(account: Account): Boolean {
        return repo.addAccount(account)
    }
}