package com.example.nineintelligence.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nineintelligence.core.AuthPrefs
import com.example.nineintelligence.core.nearestTryoutSchedule
import com.example.nineintelligence.domain.models.TakenTryOutModel
import com.example.nineintelligence.domain.use_case.profile_use_case.DetailProfileUseCase
import com.example.nineintelligence.domain.use_case.tryout_use_case.TakenTryOutUseCase
import com.example.nineintelligence.domain.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val profile: DetailProfileUseCase,
    private val prefs: AuthPrefs,
    private val takenTryOutModel: TakenTryOutUseCase
) : ViewModel() {
    private val _upcomingTryout = MutableStateFlow<TakenTryOutModel?>(null)
    val upcomingTryout = _upcomingTryout.asStateFlow()
    var userName by mutableStateOf("")
        private set
    var shouldNavigateToLoginScreen by mutableStateOf(false)
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
                    prefs.clearData()
                    shouldNavigateToLoginScreen = true
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

    private suspend fun getUpcomingTryout() {
        when (val res = takenTryOutModel.getListTakenTryOut()) {
            is Resource.Success -> {
                _upcomingTryout.update {
                    res.data?.nearestTryoutSchedule()
                }
            }

            is Resource.Error -> {

            }

            else -> {

            }
        }
    }
}