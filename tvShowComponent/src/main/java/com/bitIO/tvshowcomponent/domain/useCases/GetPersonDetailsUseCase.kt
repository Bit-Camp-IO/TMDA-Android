package com.bitIO.tvshowcomponent.domain.useCases

import com.bitIO.tvshowcomponent.domain.repository.TvShowRepository
import com.example.sharedComponent.entities.people.PersonDetails
import javax.inject.Inject

class GetPersonDetailsUseCase @Inject constructor(
    private val repository: TvShowRepository
) {

    suspend fun invoke(personId: Int): Result<PersonDetails> {
        return try {
            Result.success(repository.getPersonDetails(personId))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}