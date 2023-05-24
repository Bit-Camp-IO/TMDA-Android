package com.example.account.domain.interactors

import com.example.account.domain.repositories.IAccountRepository

class AddToWatchListInteractor(private val repository: IAccountRepository) {
    suspend operator fun invoke(accountId: Int, movieId: Int) =
        repository.addToWatchList(accountId, movieId)
}