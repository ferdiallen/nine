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
    private val _shouldShowLoadingScreen = MutableStateFlow(false)
    val shouldShowLoadingScreen = _shouldShowLoadingScreen.asStateFlow()

    init {
        viewModelScope.launch {
            getUserInfo()
        }
    }

    private fun getUserInfo() = viewModelScope.launch(Dispatchers.IO) {
        _shouldShowLoadingScreen.update {
            true
        }
        when (val res = useCase.getDetailUser()) {
            is Resource.Success -> {
                _userDataInfo.update {
                    res.data
                }
                _shouldShowLoadingScreen.update {
                    false
                }
            }

            is Resource.Loading -> {

            }

            is Resource.Error -> {

            }

            else -> {

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
                        getUserInfo()
                    }

                    is Resource.Error -> {
                        println(it.errorMessages)
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