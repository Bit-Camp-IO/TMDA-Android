package com.example.account.domain.interactors

import com.example.account.domain.repositories.IAccountRepository

class GetFavoriteTVInteractor (private val repository: IAccountRepository) {
    suspend operator fun invoke(accountId: Int) =
        repository.getFavoriteTV(accountId)
}