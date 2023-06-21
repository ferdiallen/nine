package com.example.nineintelligence.presentation.packagescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nineintelligence.domain.models.PaymentItemModel
import com.example.nineintelligence.domain.models.PaymentReceivedModel
import com.example.nineintelligence.domain.models.UserProfileModel
import com.example.nineintelligence.domain.use_case.payment_use_case.CreatePaymentUseCase
import com.example.nineintelligence.domain.use_case.payment_use_case.GetListPaymentItemsUseCase
import com.example.nineintelligence.domain.use_case.profile_use_case.DetailProfileUseCase
import com.example.nineintelligence.domain.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PackageViewModel(
    private val paymentItemsUseCase: GetListPaymentItemsUseCase,
    private val userData: DetailProfileUseCase,
    private val createPayment: CreatePaymentUseCase
) : ViewModel() {
    private val _paymentItemsList = MutableStateFlow<List<PaymentItemModel>>(emptyList())
    val paymentItemList = _paymentItemsList.asStateFlow()

    private val _userProfileData = MutableStateFlow<UserProfileModel?>(null)

    private val _paymentResult = MutableStateFlow<PaymentReceivedModel?>(null)
    val paymentResult = _paymentResult.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getListOfPayment()
            getUserData()
        }
    }

    private suspend fun getListOfPayment() {
        when (val res = paymentItemsUseCase.getItemPaymentList()) {
            is Resource.Success -> {
                _paymentItemsList.update {
                    res.data ?: emptyList()
                }
            }

            is Resource.Error -> {

            }

            else -> {

            }
        }
    }

    private suspend fun getUserData() {
        when (val data = userData.getDetailUser()) {
            is Resource.Success -> {
                _userProfileData.update {
                    data.data
                }
            }

            is Resource.Error -> {

            }

            else -> {

            }
        }
    }

    fun createPayment(data: PaymentItemModel) = viewModelScope.launch(Dispatchers.IO) {
        when (val res =
            createPayment.createPayment(data, _userProfileData.value ?: return@launch)) {
            is Resource.Success -> {
                println("Success")
                println(res.data?.token)
                _paymentResult.update {
                    res.data
                }
            }

            is Resource.Error -> {
                println(res.errorMessages)
            }

            else -> {

            }
        }
    }
}