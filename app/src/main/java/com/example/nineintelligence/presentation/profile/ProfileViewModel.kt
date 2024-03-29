package com.example.nineintelligence.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nineintelligence.core.AuthPrefs
import com.example.nineintelligence.domain.models.HistoryBankSoalTryout
import com.example.nineintelligence.domain.models.HistoryModel
import com.example.nineintelligence.domain.models.TakenBankSoal
import com.example.nineintelligence.domain.models.TakenTryOutModel
import com.example.nineintelligence.domain.models.UserProfileModel
import com.example.nineintelligence.domain.use_case.bank_soal_use_case.TakenBankSoalUseCase
import com.example.nineintelligence.domain.use_case.profile_use_case.DetailProfileUseCase
import com.example.nineintelligence.domain.use_case.profile_use_case.HistoryUseCase
import com.example.nineintelligence.domain.use_case.profile_use_case.UpdateProfileUseCase
import com.example.nineintelligence.domain.use_case.tryout_use_case.TakenTryOutUseCase
import com.example.nineintelligence.domain.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val store: AuthPrefs,
    private val useCase: DetailProfileUseCase,
    private val updateUseCase: UpdateProfileUseCase,
    private val listTakenTryOut: TakenTryOutUseCase,
    private val historyUseCase: HistoryUseCase,
    private val takenBankSoalUseCase: TakenBankSoalUseCase
) : ViewModel() {
    private val _userDataInfo = MutableStateFlow<UserProfileModel?>(null)
    val userDataInfo = _userDataInfo.asStateFlow()
    private val _shouldShowLoadingScreen = MutableStateFlow(false)
    val shouldShowLoadingScreen = _shouldShowLoadingScreen.asStateFlow()

    private val _listTakenTryOut = MutableStateFlow<List<TakenTryOutModel>>(emptyList())
    val listTakenTryOutModel = _listTakenTryOut.asStateFlow()

    private val _userHistory = MutableStateFlow<HistoryBankSoalTryout?>(null)
    val userHistory = _userHistory.asStateFlow()

    private val _takenBankSoalList = MutableStateFlow<List<TakenBankSoal>>(emptyList())
    val takenBankSoal = _takenBankSoalList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getUserInfo()
            getTakenTryOut()
            getUserHistory()
            getListTakenBankSoal()
        }
    }

    private suspend fun getListTakenBankSoal() {
        when (val res = takenBankSoalUseCase.getListTakenBankSoal()) {
            is Resource.Success -> {
                _takenBankSoalList.update {
                    res.data ?: emptyList()
                }
            }

            is Resource.Error -> {

            }

            else -> {

            }
        }
    }

    private suspend fun getUserHistory() {
        when (val res = historyUseCase.getHistory()) {
            is Resource.Success -> {
                _userHistory.update {
                    res.data ?: return
                }
            }

            is Resource.Error -> {
                println(res.errorMessages)
            }

            else -> {

            }
        }
    }


    private suspend fun getUserInfo() {
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
                _shouldShowLoadingScreen.update {
                    false
                }
                println(res.errorMessages)
            }

            else -> {

            }
        }
    }

    fun clearData() = viewModelScope.launch {
        store.clearData()
    }

    private suspend fun getTakenTryOut() {
        when (val res = listTakenTryOut.getListTakenTryOut()) {
            is Resource.Success -> {
                _listTakenTryOut.update {
                    res.data ?: emptyList()
                }
            }

            is Resource.Error -> {

            }

            is Resource.Loading -> {

            }

            is Resource.Empty -> {

            }
        }
    }

    fun updateData(
        userId: String,
        username: String,
        userEmail: String,
        phone: String,
        address: String,
        profilePic: String,
        gender: String
    ) = viewModelScope.launch(Dispatchers.IO) {
        updateUseCase.updateProfile(
            userId,
            username,
            userEmail,
            phone,
            address,
            profilePic,
            gender
        ).run {
            when (this) {
                is Resource.Success -> {
                    getUserInfo()
                }

                is Resource.Error -> {
                    println(errorMessages)
                }

                else -> {
                    println("Failed")
                }
            }
        }
    }
}