package com.example.nineintelligence.presentation.enter

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nineintelligence.R
import com.example.nineintelligence.data.network.apiservice.RegisterUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val user: RegisterUser
) : ViewModel() {
    var userFirstName by mutableStateOf("")
        private set
    var userLastName by mutableStateOf("")
        private set
    var userEmail by mutableStateOf("")
        private set
    var userPhoneNumber by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var confirmPassword by mutableStateOf("")
        private set

    fun setDataFor(data: String, type: String, context: Context) {
        when (type) {
            context.getString(R.string.userfirstname) -> {
                userFirstName = data
            }

            context.getString(R.string.userlastname) -> {
                userLastName = data
            }

            context.getString(R.string.useremail) -> {
                userEmail = data
            }

            context.getString(R.string.userphonenumber) -> {
                userPhoneNumber = data
            }

            context.getString(R.string.password) -> {
                password = data
            }

            context.getString(R.string.confirmpassword) -> {
                confirmPassword = data
            }
        }
    }

    fun registerUser(name: String, email: String, password: String) =
        viewModelScope.launch(Dispatchers.IO) {
            val register = user.registerUser(name, email, password)
            register?.let {
                println(it.userName)
            }
        }
}