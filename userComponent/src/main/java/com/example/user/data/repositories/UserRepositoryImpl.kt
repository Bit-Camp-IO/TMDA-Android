package com.example.user.data.repositories

import com.example.user.data.dto.RequestTokenDetailsDto
import com.example.user.data.local.UserDao
import com.example.user.data.mappers.toRequestBody
import com.example.user.data.mappers.toUserDetails
import com.example.user.data.remote.UserApiServices
import com.example.user.domain.entities.User
import com.example.user.domain.entities.UserDetails
import com.example.user.domain.entities.UserFirstLoginState
import com.example.user.domain.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApiServices: UserApiServices,
    private val userDao: UserDao
) : UserRepository {
    private val userState: MutableStateFlow<User?> = MutableStateFlow(cachedUser)


    init {
        CoroutineScope(Dispatchers.IO).launch(context = Dispatchers.IO) {
            if (cachedUser == null) {
                cachedUser = userDao.getUser()
                userState.emit(cachedUser)
            }
        }
    }

    override suspend fun createLoginSession(userName: String, password: String): User {
        val activeToken = getActiveToken(userName, password)
        val sessionId = userApiServices.createSession(activeToken.toRequestBody()).sessionId
        val user = User(userName, password, sessionId)
        cachedUser = user
        userDao.saveCurrentUser(user)
        userState.value = cachedUser
        userDao.insertUserFirstTime(UserFirstLoginState(false))
        return user
    }

    override suspend fun getCurrentUser(): User {
        if (cachedUser == null)
            cachedUser = userDao.getUser()
        return cachedUser!!
    }

    override fun getUserState(): Flow<User?> {
        return userState
    }

    override suspend fun deleteSession(): Boolean {
        val isSessionDeleted = userApiServices.deleteSession(cachedUser!!.sessionId.toRequestBody())
        if (isSessionDeleted.success) {
            cachedUser = null
            userDao.deleteCurrentUser()
            userState.value = cachedUser
        }
        return isSessionDeleted.success
    }

//    override suspend fun addSeriesToWatchList(seriesId: Int, isAddRequest: Boolean) {
//        userApiServices.postToWatchList(
//            accountId = ACCOUNT_ID,
//            sessionId = cachedUser!!.sessionId,
//            body = makePostToWatchListBody(type = "tv", mediaId = seriesId, isAddRequest)
//        )
//    }

//    override suspend fun addMovieToWatchList(movieId: Int, isAddRequest: Boolean) {
//        userApiServices.postToWatchList(
//            accountId = ACCOUNT_ID,
//            sessionId = cachedUser!!.sessionId,
//            body = makePostToWatchListBody(type = "movie", mediaId = movieId, isAddRequest)
//        )
//    }

//    override suspend fun getTvSavedState(seriesId: Int): Boolean {
//        return userApiServices.getTvSavedState(seriesId, cachedUser!!.sessionId).watchList
//    }

    override suspend fun getUserDetails(): UserDetails {
        return userApiServices.getUserDetails(
            accountId = 16874876,
            sessionId = cachedUser!!.sessionId
        ).toUserDetails()
    }

    override suspend fun getIsFirstUserLogin(): Boolean {
        return userDao.getUserFirstLoginState()?.isUSerFirstLogin?:true
    }


    //Helpers
    private suspend fun getActiveToken(
        userName: String,
        password: String
    ): RequestTokenDetailsDto {
        val requestToken = userApiServices.getRequestToken().requestToken
        val requestBody = makeLoginRequestBody(userName, password, requestToken)
        return userApiServices.createActiveTokenWithLogin(requestBody)
    }

    private fun makeLoginRequestBody(
        userName: String,
        password: String,
        requestToken: String
    ): RequestBody {
        val mediaType = MediaType.parse("application/json")
        val content =
            "{\"username\":\"$userName\",\"password\":\"$password\",\"request_token\":\"$requestToken\"}"
        return RequestBody.create(mediaType, content)


    }



    companion object {
        private var cachedUser: User? = null
    }
}