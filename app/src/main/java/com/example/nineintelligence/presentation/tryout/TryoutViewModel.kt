package com.example.nineintelligence.presentation.tryout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nineintelligence.domain.models.TryoutDataModel
import com.example.nineintelligence.domain.use_case.tryout_use_case.TryoutUseCase
import com.example.nineintelligence.domain.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TryoutViewModel(
    private val tryout: TryoutUseCase
) : ViewModel() {
    private val _tryoutState = MutableStateFlow<List<TryoutDataModel>>(emptyList())
    val tryoutState = _tryoutState.asStateFlow()

    init {
        viewModelScope.launch {
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
    }
}