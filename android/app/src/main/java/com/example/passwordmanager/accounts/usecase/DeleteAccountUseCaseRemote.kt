package com.example.passwordmanager.accounts.usecase

import com.example.passwordmanager.accounts.domain.Account
import com.example.passwordmanager.accounts.repo.AccountRepository
import javax.inject.Inject

interface DeleteAccountUseCaseRemote {
    suspend operator fun invoke(accountID: Long): Boolean
}

class DeleteAccountUseCaseRemoteImpl @Inject constructor(
    val repo: AccountRepository
) : DeleteAccountUseCaseRemote {
    override suspend fun invoke(accountID: Long): Boolean {
        return repo.deleteAccountRemote(accountID)
    }
}