package com.example.moviesComponent.domain.useCases

import com.example.moviesComponent.domain.repositories.MoviesRepository
import com.example.sharedComponent.entities.people.PersonDetails
import javax.inject.Inject

class GetPersonDetailsUseCase @Inject constructor(private val repository: MoviesRepository) {
    suspend fun invoke(personId:Int): Result<PersonDetails> {
        return try {
            Result.success(repository.getPersonDetails(personId))
        }catch (e:Throwable){
            Result.failure(e)
        }
    }
}