package com.example.tmda.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.user.domain.useCases.GetSessionStateStreamUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@FlowPreview
@HiltViewModel
class MainViewModel @Inject constructor(
    private val getSessionStateStreamUseCase: GetSessionStateStreamUseCase,

) : ViewModel() {
    private val _userState: MutableState<LoginState> = mutableStateOf(LoginState.Loading)
    val userState: State<LoginState>
        get() = _userState



    init {
        _userState.value = LoginState.Loading
        observeUserState()
    }



    private fun observeUserState() {
        viewModelScope.launch {
            getUserFlow().debounce(200).collect {
                withContext(Dispatchers.Main){
                    _userState.value = if (it == null) LoginState.LoggedOut else LoginState.LoggedIn
                }

            }
        }

    }

    private fun getUserFlow() = getSessionStateStreamUseCase.invoke()
    enum class LoginState {
        LoggedIn,
        LoggedOut,
        Loading
    }
}