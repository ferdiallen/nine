package com.example.nineintelligence.presentation.enter

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nineintelligence.core.AuthPrefs
import com.example.nineintelligence.data.network.apiservice.LoginUser
import com.example.nineintelligence.domain.models.LoginModel
import com.example.nineintelligence.domain.models.UserModel
import com.example.nineintelligence.domain.use_case.login_use_case.LoginUseCase
import com.example.nineintelligence.domain.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class EnterViewModel(
    private val login: LoginUseCase,
    private val store: AuthPrefs
) : ViewModel() {
    private val _currentEmail = MutableStateFlow("")
    private val _currentPassword = MutableStateFlow("")
    private val _isCheckedRememberMe = MutableStateFlow(false)
    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState
    val currentEmail = _currentEmail.asStateFlow()
    val currentPassword = _currentPassword.asStateFlow()
    val isCheckedRememberMe = _isCheckedRememberMe.asStateFlow()

    fun onEmailChange(text: String) = _currentEmail.update { text }
    fun onPasswordChange(text: String) = _currentPassword.update { text }
    fun onCheckedChange(data: Boolean) = _isCheckedRememberMe.update { data }

    fun loginUser(username: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        login.getUserAuth(username, password).let { out ->
            when (out) {
                is Resource.Success -> {
                    store.saveToken(out.data?.token ?: "")
                    _loginState.value = LoginState(false, out.data?.token, "")
                }

                is Resource.Error -> {
                    _loginState.value = LoginState(false, null, out.errorMessages ?: "")
                }

                is Resource.Loading -> {
                    _loginState.value = LoginState(true, null, "")
                }

                else -> {}
            }
        }
    }
}