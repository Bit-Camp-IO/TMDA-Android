package com.example.account.domain.interactors

import com.example.account.domain.repositories.IAccountRepository

class GetRatedTVInteractor(private val repository: IAccountRepository) {
    suspend operator fun invoke(accountId: Int) =
        repository.getRatedTV(accountId)
}