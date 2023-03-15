package com.example.nineintelligence.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nineintelligence.core.AuthPrefs
import com.example.nineintelligence.domain.models.UserProfileModel
import com.example.nineintelligence.domain.use_case.profile_use_case.DetailProfileUseCase
import com.example.nineintelligence.domain.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val store: AuthPrefs,
    private val useCase: DetailProfileUseCase
) : ViewModel() {
    private val _userDataInfo = MutableStateFlow<UserProfileModel?>(null)
    val userDataInfo = _userDataInfo.asStateFlow()

    init {
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

                }

                else -> {

                }
            }
        }
    }

    fun clearData() = viewModelScope.launch {
        store.clearData()
    }
}