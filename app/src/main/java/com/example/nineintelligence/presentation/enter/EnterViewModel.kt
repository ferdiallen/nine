package com.example.nineintelligence.presentation.enter

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nineintelligence.core.AuthPrefs
import com.example.nineintelligence.domain.use_case.login_use_case.LoginUseCase
import com.example.nineintelligence.domain.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.random.nextInt

class EnterViewModel(
    private val login: LoginUseCase,
    private val store: AuthPrefs
) : ViewModel() {
    private val _currentEmail = MutableStateFlow("")
    private val _currentPassword = MutableStateFlow("")
    private val _isCheckedRememberMe = MutableStateFlow(false)
    private val _loginState: MutableState<LoginState?> = mutableStateOf(null)
    var isLoadingLogin by mutableStateOf(false)
        private set
    val loginState: State<LoginState?> = _loginState
    val currentEmail = _currentEmail.asStateFlow()
    val currentPassword = _currentPassword.asStateFlow()
    val isCheckedRememberMe = _isCheckedRememberMe.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _isCheckedRememberMe.update {
                store.isRememberSaved(userName = { username ->
                    _currentEmail.update {
                        username
                    }
                }, password = { password ->
                    _currentPassword.update {
                        password
                    }
                })
            }
        }
    }

    fun onEmailChange(text: String) = _currentEmail.update { text }
    fun onPasswordChange(text: String) = _currentPassword.update { text }
    fun onCheckedChange(data: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            if (!data && store.isRememberSaved(userName = {}, password = {})) {
                store.deleteSavedUser()
            }
        }
        _isCheckedRememberMe.update { data }
    }


    fun loginUser(username: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        isLoadingLogin = true
        login.getUserAuth(username, password).let { out ->
            when (out) {
                is Resource.Success -> {
                    isLoadingLogin = false
                    if (isCheckedRememberMe.first()) {
                        store.rememberUser(username, password)
                    }
                    store.saveToken(out.data?.token ?: return@launch)
                    _loginState.value = LoginState(false, out.data.token, "")
                }

                is Resource.Error -> {
                    isLoadingLogin = false
                    _loginState.value = LoginState(
                        false, null,
                        out.errorMessages + Random.nextInt(0,100)
                    )
                }

                is Resource.Loading -> {
                    _loginState.value = LoginState(true, null, "")
                }

                else -> {}
            }
        }
    }
}