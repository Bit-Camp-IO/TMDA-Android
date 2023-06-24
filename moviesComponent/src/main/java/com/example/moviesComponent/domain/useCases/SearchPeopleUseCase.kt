package com.example.moviesComponent.domain.useCases

import com.example.moviesComponent.domain.repositories.MoviesRepository
import com.example.sharedComponent.entities.people.PeoplePage
import javax.inject.Inject

class SearchPeopleUseCase @Inject constructor(private val repository: MoviesRepository) {
    suspend fun invoke(keyword:String,pageIndex:Int): Result<PeoplePage> {
        return try {
            Result.success(repository.searchPeople(keyword,pageIndex))
        }catch (e:Throwable){
            Result.failure(e)
        }
    }

}