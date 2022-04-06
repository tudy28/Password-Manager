package com.example.passwordmanager.accounts.usecase

import com.example.passwordmanager.accounts.domain.Account
import com.example.passwordmanager.accounts.repo.AccountRepository
import javax.inject.Inject

interface UpdateAccountUseCase {
    suspend operator fun invoke(newAccount: Account)
}

class UpdateAccountUseCaseImpl @Inject constructor(
    val repo: AccountRepository
) : UpdateAccountUseCase {
    override suspend fun invoke(newAccount: Account) {
        return repo.updateAccount(newAccount)
    }
}