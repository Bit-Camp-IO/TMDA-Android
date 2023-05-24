package com.example.account.domain.interactors

import com.example.account.domain.repositories.IAccountRepository

class GetRatedEpisodesInteractor(private val repository: IAccountRepository) {
    suspend operator fun invoke(accountId: Int) =
        repository.getRatedEpisodes(accountId)
}