package com.example.tmda.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.user.domain.useCases.GetIsFirstUserLoginUseCase
import com.example.user.domain.useCases.LoginUseCase
import com.example.tmda.presentation.shared.uiStates.UiState
import com.example.tmda.presentation.shared.uiStates.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val getIsFirstUserLoginUseCase: GetIsFirstUserLoginUseCase
) : ViewModel() {
    var userName by mutableStateOf(value = "")
    var password by mutableStateOf(value = "")
    private val _errorMsg = mutableStateOf("")
    val errorMsg: State<String>
        get() = _errorMsg
    private val _isFirstLogin = mutableStateOf<UiState<Boolean>>(UiState.Loading())
     val isFirstLogin
         get() = _isFirstLogin

    init {
        updateIsFirstLogin()
    }
    fun login() =
        viewModelScope.launch(context = Dispatchers.IO) {

            val x = loginUseCase.invoke(userName, password)
            x.onFailure {
                if (it is HttpException)
                    _errorMsg.value = "invalid user name or password"
                else
                    _errorMsg.value = "no internet connection"
                delay(1000)
                _errorMsg.value = ""
            }

        }
  private fun updateIsFirstLogin(){
       viewModelScope.launch(Dispatchers.IO) {
           val state=getIsFirstUserLoginUseCase.invoke()
           withContext(Dispatchers.Main){
               _isFirstLogin.value =state.toUiState()
           }

       }

    }

}