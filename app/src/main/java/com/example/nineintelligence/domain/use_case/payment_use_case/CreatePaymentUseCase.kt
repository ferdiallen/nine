package com.example.nineintelligence.domain.use_case.payment_use_case

import com.example.nineintelligence.data.network.apiservice.CreatePayment
import com.example.nineintelligence.domain.models.PaymentItemModel
import com.example.nineintelligence.domain.models.PaymentReceivedModel
import com.example.nineintelligence.domain.models.UserProfileModel
import com.example.nineintelligence.domain.util.Resource
import java.io.IOException

class CreatePaymentUseCase(
    private val createPayment: CreatePayment
) {
    suspend fun createPayment(
        paymentInfo: PaymentItemModel,
        userData: UserProfileModel
    ): Resource<PaymentReceivedModel> {
        return try {
            val payment = createPayment.createPayment(paymentInfo, userData)
            Resource.Success(payment)
        } catch (e: Exception) {
            Resource.Error(e.message)
        } catch (e: IOException) {
            Resource.Error(e.message)
        }
    }
}