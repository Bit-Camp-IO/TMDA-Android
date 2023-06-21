package com.example.tmda.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authentication.domain.useCases.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {
    var userName by mutableStateOf(value = "")
    var password by mutableStateOf(value = "")
    private val _errorMsg = mutableStateOf("")
    val errorMsg: State<String>
        get() = _errorMsg

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


}