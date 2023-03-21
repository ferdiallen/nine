package com.example.nineintelligence.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nineintelligence.core.AuthPrefs
import com.example.nineintelligence.domain.models.UserProfileModel
import com.example.nineintelligence.domain.use_case.profile_use_case.DetailProfileUseCase
import com.example.nineintelligence.domain.use_case.profile_use_case.UpdateProfileUseCase
import com.example.nineintelligence.domain.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val store: AuthPrefs,
    private val useCase: DetailProfileUseCase,
    private val updateUseCase: UpdateProfileUseCase
) : ViewModel() {
    private val _userDataInfo = MutableStateFlow<UserProfileModel?>(null)
    val userDataInfo = _userDataInfo.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            store.saveToken(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiYzdlYWU2YTktN" +
                        "jA5MC00MDI2LWI3YmMtOTdjOGNmZjQ0ZWIwIiwiZXhwIjoxNjc5MzAxNjUwfQ.zQptXXK89Tl-N4pFqlO3yOPSfQA_jiqlZyYFedkcE3M"
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            val res = useCase.getDetailUser()
            when (res) {
                is Resource.Success -> {
                    _userDataInfo.update {
                        res.data
                    }
                }

                is Resource.Loading -> {

                }

                is Resource.Error -> {
                    println(res.errorMessages)
                }

                else -> {

                }
            }
        }
    }

    fun clearData() = viewModelScope.launch {
        store.clearData()
    }

    fun updateData(
        username: String,
        userEmail: String,
        password: String,
        phone: String,
        address: String,
        profilePic: String,
        gender: String
    ) =
        viewModelScope.launch(Dispatchers.IO) {
            updateUseCase.updateProfile(
                username,
                userEmail,
                password,
                phone,
                address,
                profilePic,
                gender
            ).let {
                when (it) {
                    is Resource.Success -> {
                        println("Success")
                    }

                    is Resource.Error -> {
                        println("Failed")
                    }

                    else -> {
                    }
                }
            }
        }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            store.clearData()
        }
    }
}