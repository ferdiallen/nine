package com.example.nineintelligence.presentation.banksoal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nineintelligence.domain.models.BankSoalModel
import com.example.nineintelligence.domain.use_case.bank_soal_use_case.GetListBankSoalUseCase
import com.example.nineintelligence.domain.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BankSoalViewModel(
    private val getListBankSoalUseCase: GetListBankSoalUseCase
) : ViewModel() {
    private val _bankSoalModelList = MutableStateFlow<List<BankSoalModel>>(emptyList())
    val bankSoalList = _bankSoalModelList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getListBankSoal()
        }
    }

    private suspend fun getListBankSoal() {
        when (val res = getListBankSoalUseCase.getBankSoalList()) {
            is Resource.Success -> {
                _bankSoalModelList.update {
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