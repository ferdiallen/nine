package com.example.nineintelligence.presentation.banksoal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nineintelligence.domain.models.BankSoalModel
import com.example.nineintelligence.domain.models.BankSoalRegisterState
import com.example.nineintelligence.domain.models.TakenBankSoal
import com.example.nineintelligence.domain.use_case.bank_soal_use_case.GetListBankSoalUseCase
import com.example.nineintelligence.domain.use_case.bank_soal_use_case.TakeBankSoalUseCase
import com.example.nineintelligence.domain.use_case.bank_soal_use_case.TakenBankSoalUseCase
import com.example.nineintelligence.domain.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BankSoalViewModel(
    private val getListBankSoalUseCase: GetListBankSoalUseCase,
    private val takeBankSoalUseCase: TakeBankSoalUseCase,
    private val takenBankSoalUseCase: TakenBankSoalUseCase
) : ViewModel() {
    private val _bankSoalModelList = MutableStateFlow<List<BankSoalModel>>(emptyList())
    val bankSoalList = _bankSoalModelList.asStateFlow()

    private val _takenBankSoal = MutableStateFlow<List<TakenBankSoal>>(emptyList())
    val takenBankSoal = _takenBankSoal.asStateFlow()

    private val _registerStateBankSoal = MutableStateFlow<BankSoalRegisterState?>(null)
    val registerStateBankSoal = _registerStateBankSoal.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getListBankSoal()
            showListTakenBankSoal()
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

    private suspend fun showListTakenBankSoal() {
        when (val res = takenBankSoalUseCase.getListTakenBankSoal()) {
            is Resource.Success -> {
                _takenBankSoal.update {
                    res.data ?: emptyList()
                }
            }

            is Resource.Error -> {}
            else -> {

            }
        }
    }

    fun takeBankSoal(slugName: String, title: String) = viewModelScope.launch(Dispatchers.IO) {
        when (val res = takeBankSoalUseCase.takeBankSoal(slugName)) {
            is Resource.Success -> {
                _registerStateBankSoal.update {
                    BankSoalRegisterState("Berhasil mendaftar : $title")
                }
                showListTakenBankSoal()
            }

            is Resource.Error -> {
                _registerStateBankSoal.update {
                    BankSoalRegisterState(
                        title,
                        errorDescription = if (res.errorMessages?.contains("You have taken") == true)

                            "Sudah mendaftar" else res.errorMessages
                            ?: " Unknown error"
                    )
                }
            }

            else -> {

            }
        }
    }
}