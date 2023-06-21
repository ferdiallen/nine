package com.example.nineintelligence.data.network.apiservice

import com.example.nineintelligence.domain.models.PaymentItemModel
import com.example.nineintelligence.domain.models.PaymentReceivedModel
import com.example.nineintelligence.domain.models.UserProfileModel

interface CreatePayment {
    suspend fun createPayment(
        paymentInfo: PaymentItemModel,
        userData: UserProfileModel
    ): PaymentReceivedModel
}