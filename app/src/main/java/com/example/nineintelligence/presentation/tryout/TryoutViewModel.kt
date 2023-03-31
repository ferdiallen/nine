package com.example.nineintelligence.presentation.tryout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nineintelligence.core.AuthPrefs
import com.example.nineintelligence.domain.models.TryoutDataModel
import com.example.nineintelligence.domain.use_case.tryout_use_case.TakeTryOutUseCase
import com.example.nineintelligence.domain.use_case.tryout_use_case.TakenTryOutUseCase
import com.example.nineintelligence.domain.use_case.tryout_use_case.TryoutUseCase
import com.example.nineintelligence.domain.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TryoutViewModel(
    private val tryout: TryoutUseCase,
    private val signTryout: TakeTryOutUseCase,
    private val takenTryOutUseCase: TakenTryOutUseCase
) : ViewModel() {
    private val _tryoutState = MutableStateFlow<List<TryoutDataModel>>(emptyList())
    val tryoutState = _tryoutState.asStateFlow()
    private val _hasTakenTryOut = MutableStateFlow<List<String>>(emptyList())
    val hasTakenTryOut = _hasTakenTryOut.asStateFlow()

    init {
        viewModelScope.launch {
            async { checkWhetherHasTakenTryOut() }
            async { getListTryOut() }
        }
    }

    private suspend fun getListTryOut() {
        when (val res = tryout.getListTryOut()) {
            is Resource.Success -> {
                _tryoutState.update {
                    res.data ?: emptyList()
                }
            }

            is Resource.Loading -> {
                _tryoutState.update {
                    emptyList()
                }
            }

            is Resource.Error -> {
                _tryoutState.update {
                    emptyList()
                }
            }

            is Resource.Empty -> {
                _tryoutState.update {
                    emptyList()
                }
            }
        }
    }

    private fun checkWhetherHasTakenTryOut() = viewModelScope.launch(Dispatchers.IO) {
        when (val res = takenTryOutUseCase.getListTakenTryOut()) {
            is Resource.Success -> {
                _hasTakenTryOut.update {
                    res.data?.map { out ->
                        out.tryoutDetails?.tryOutSlug.toString()
                    } ?: emptyList()
                }
            }

            is Resource.Error -> {
                println(res.errorMessages)
            }

            is Resource.Loading -> {

            }

            is Resource.Empty -> {

            }
        }
    }

    fun signTryOut(slugName: String) = viewModelScope.launch(Dispatchers.IO) {
        val res = signTryout.takeTryOut(slugName)
        when (res) {
            is Resource.Success -> {
                checkWhetherHasTakenTryOut()
                println(res.data?.type)
            }

            is Resource.Loading -> {
                println("Loading")
            }

            is Resource.Error -> {
                println("Failed")
            }

            is Resource.Empty -> {
                println("Empty")
            }
        }
    }
}