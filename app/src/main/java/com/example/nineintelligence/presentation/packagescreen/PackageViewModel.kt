package com.example.nineintelligence.presentation.packagescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nineintelligence.domain.models.PaymentItemModel
import com.example.nineintelligence.domain.use_case.payment_use_case.GetListPaymentItemsUseCase
import com.example.nineintelligence.domain.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PackageViewModel(
    private val paymentItemsUseCase: GetListPaymentItemsUseCase
) : ViewModel() {
    private val _paymentItemsList = MutableStateFlow<List<PaymentItemModel>>(emptyList())
    val paymentItemList = _paymentItemsList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getListOfPayment()
        }
    }

    private suspend fun getListOfPayment() {
        when (val res = paymentItemsUseCase.getItemPaymentList()) {
            is Resource.Success -> {
                _paymentItemsList.update {
                    res?.data ?: emptyList()
                }
            }

            is Resource.Error -> {

            }

            else -> {}
        }
    }
}