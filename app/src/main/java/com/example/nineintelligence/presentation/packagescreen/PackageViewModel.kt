package com.example.nineintelligence.presentation.packagescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nineintelligence.domain.models.LatestPaymentResponse
import com.example.nineintelligence.domain.models.PaymentItemModel
import com.example.nineintelligence.domain.models.PaymentReceivedModel
import com.example.nineintelligence.domain.models.UserProfileModel
import com.example.nineintelligence.domain.use_case.payment_use_case.CreatePaymentUseCase
import com.example.nineintelligence.domain.use_case.payment_use_case.DeletePaymentUseCase
import com.example.nineintelligence.domain.use_case.payment_use_case.GetListPaymentItemsUseCase
import com.example.nineintelligence.domain.use_case.payment_use_case.LatestPaymentUseCase
import com.example.nineintelligence.domain.use_case.payment_use_case.UpdatePaymentUseCase
import com.example.nineintelligence.domain.use_case.profile_use_case.DetailProfileUseCase
import com.example.nineintelligence.domain.util.GeneratedInvoiceState
import com.example.nineintelligence.domain.util.PaymentState
import com.example.nineintelligence.domain.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PackageViewModel(
    private val paymentItemsUseCase: GetListPaymentItemsUseCase,
    private val userData: DetailProfileUseCase,
    private val createPayment: CreatePaymentUseCase,
    private val updatePayment: UpdatePaymentUseCase,
    private val latestPaymentUseCase: LatestPaymentUseCase,
    private val deleteOrder: DeletePaymentUseCase
) : ViewModel() {
    private val _paymentItemsList = MutableStateFlow<List<PaymentItemModel>>(emptyList())
    val paymentItemList = _paymentItemsList.asStateFlow()

    private val _paymentLatest = MutableStateFlow<LatestPaymentResponse?>(null)

    private val _paymentState = MutableStateFlow(PaymentState.IDLE)
    val paymentState = _paymentState.asStateFlow()

    private val _userProfileData = MutableStateFlow<UserProfileModel?>(null)

    private val _paymentResult = MutableStateFlow<PaymentReceivedModel?>(null)
    val paymentResult = _paymentResult.asStateFlow()

    private val _updatePaymentState = MutableStateFlow(GeneratedInvoiceState.IDLE)
    val updatePaymentState = _updatePaymentState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getListOfPayment()
            getUserData()
        }
    }

    fun retrieveLatestPayment() = viewModelScope.launch(Dispatchers.IO) {
        when (val res = latestPaymentUseCase.getLatestTransaction()) {
            is Resource.Success -> {
                _paymentLatest.update { res.data }
                updatePayment(res.data?.orderId ?: return@launch)
            }

            is Resource.Error -> {
                updatePaymentState(PaymentState.FAILED)
            }

            else -> {}
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

    fun updatePaymentState(payment: PaymentState) {
        _paymentState.update {
            payment
        }
    }

    fun createPayment(data: PaymentItemModel) = viewModelScope.launch(Dispatchers.IO) {
        when (val res =
            createPayment.createPayment(data, _userProfileData.value ?: return@launch)) {
            is Resource.Success -> {
                _paymentResult.update {
                    res.data
                }
            }

            is Resource.Error -> {
                _updatePaymentState.update {
                    GeneratedInvoiceState.FAILED
                }
            }

            else -> {

            }
        }
    }

    private fun updatePayment(orderId: String) = viewModelScope.launch(Dispatchers.IO) {
        when (val res = updatePayment.updatePayment(orderId)) {
            is Resource.Success -> {
                _updatePaymentState.update {
                    GeneratedInvoiceState.SUCCESS
                }
            }

            is Resource.Error -> {
                deletePayment()
                _updatePaymentState.update {
                    GeneratedInvoiceState.FAILED
                }
            }

            else -> {
                _updatePaymentState.update {
                    GeneratedInvoiceState.IDLE
                }
            }
        }
    }

    fun deletePayment() = viewModelScope.launch(Dispatchers.IO) {
        when (val res = deleteOrder.deleteOrder(_paymentLatest.value?.orderId ?: return@launch)) {
            is Resource.Success -> {
                res.data?.let {
                    println("Deleted Successfully")
                }
            }

            is Resource.Error -> {}
            else -> {}
        }
    }
}