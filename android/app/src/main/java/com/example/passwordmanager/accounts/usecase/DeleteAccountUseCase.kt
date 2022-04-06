package com.example.passwordmanager.accounts.usecase

import com.example.passwordmanager.accounts.domain.Account
import com.example.passwordmanager.accounts.repo.AccountRepository
import javax.inject.Inject

interface DeleteAccountUseCase {
    suspend operator fun invoke(accountID: Long): Boolean
}

class DeleteAccountUseCaseImpl @Inject constructor(
    val repo: AccountRepository
) : DeleteAccountUseCase {
    override suspend fun invoke(accountID: Long): Boolean {
        return repo.deleteAccount(accountID)
    }
}