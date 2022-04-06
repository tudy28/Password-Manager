package com.example.passwordmanager.accounts.usecase

import com.example.passwordmanager.accounts.domain.Account
import com.example.passwordmanager.accounts.repo.AccountRepository
import javax.inject.Inject

interface SyncAccountsUseCase {
    suspend operator fun invoke()
}

class SyncAccountsUseCaseImpl @Inject constructor(
    val repo: AccountRepository
) : SyncAccountsUseCase {
    override suspend fun invoke() {
        return repo.syncAccounts()
    }
}