package com.example.passwordmanager.accounts.usecase

import com.example.passwordmanager.accounts.domain.Account
import com.example.passwordmanager.accounts.repo.AccountRepository
import javax.inject.Inject

interface GetOneAccountUseCase {
    operator fun invoke(accountID: Long): Account
}

class GetOneAccountUseCaseImpl @Inject constructor(
    val repo: AccountRepository
) : GetOneAccountUseCase {
    override fun invoke(accountID: Long): Account {
        return repo.getOneAccount(accountID)
    }
}