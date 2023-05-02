package com.example.nineintelligence.presentation.tryout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nineintelligence.domain.models.GetSoalModel
import com.example.nineintelligence.domain.models.TakenTryOutModel
import com.example.nineintelligence.domain.use_case.exam_use_case.GetSoalUseCase
import com.example.nineintelligence.domain.use_case.tryout_use_case.StartTryoutUseCase
import com.example.nineintelligence.domain.use_case.tryout_use_case.TakenTryOutUseCase
import com.example.nineintelligence.domain.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TryOutInformationViewModel(
    private val takenTryOutUseCase: TakenTryOutUseCase,
    private val getSoalUseCase: GetSoalUseCase,
    private val startTryoutUseCase: StartTryoutUseCase
) : ViewModel() {
    private val _takenTryOutInformation = MutableStateFlow<TakenTryOutModel?>(null)
    val takenTryOutInformation = _takenTryOutInformation.asStateFlow()

    private val _getSoal = MutableStateFlow<List<GetSoalModel>>(emptyList())
    val getSoal = _getSoal.asStateFlow()

    fun startTryout(slugname: String) = viewModelScope.launch(Dispatchers.IO) {
        startTryoutUseCase.startTryout(slugname).let {
            when (it) {
                is Resource.Success -> {
                    println(it.data?.duration)
                }

                is Resource.Error -> {

                }

                else -> {

                }
            }
        }
    }

    suspend fun readTryOutRequiredInformation(slugname: String) {
        when (val res = takenTryOutUseCase.getListTakenTryOut()) {
            is Resource.Success -> {
                _takenTryOutInformation.update {
                    res.data?.find {
                        it.tryoutDetails?.tryOutSlug == slugname
                    }
                }
            }

            is Resource.Error -> {

            }

            else -> {

            }
        }
    }

    suspend fun readSoalList(slugname: String) {
        return when (val res = getSoalUseCase.getSoal(slugname)) {
            is Resource.Success -> {
                _getSoal.update {
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
}