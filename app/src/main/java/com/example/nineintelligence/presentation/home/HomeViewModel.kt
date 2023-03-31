package com.example.nineintelligence.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nineintelligence.core.AuthPrefs
import com.example.nineintelligence.domain.use_case.profile_use_case.DetailProfileUseCase
import com.example.nineintelligence.domain.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val profile: DetailProfileUseCase
) : ViewModel() {

    var userName by mutableStateOf("")
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getUserName()
        }
    }

    private suspend fun getUserName() {
        profile.getDetailUser().let {
            when (it) {
                is Resource.Success -> {
                    userName = it.data?.userName.toString()
                }

                is Resource.Error -> {
                    userName = "Unknown"
                }

                is Resource.Loading -> {
                    userName = ""
                }

                is Resource.Empty -> {

                }
            }
        }
    }
}