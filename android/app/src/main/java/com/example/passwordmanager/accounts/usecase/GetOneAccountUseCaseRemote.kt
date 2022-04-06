package com.example.passwordmanager.accounts.usecase

import com.example.passwordmanager.accounts.domain.Account
import com.example.passwordmanager.accounts.repo.AccountRepository
import javax.inject.Inject

interface GetOneAccountUseCaseRemote {
    suspend operator fun invoke(accountID: Long): Account
}

class GetOneAccountUseCaseRemoteImpl @Inject constructor(
    val repo: AccountRepository
) : GetOneAccountUseCaseRemote {
    override suspend fun invoke(accountID: Long): Account {
        return repo.getOneAccountRemote(accountID)
    }
}